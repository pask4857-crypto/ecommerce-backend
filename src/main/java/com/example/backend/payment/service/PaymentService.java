package com.example.backend.payment.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.backend.order.entity.Order;
import com.example.backend.order.repository.OrderRepository;
import com.example.backend.order.service.OrderService;
import com.example.backend.payment.dto.PaymentCreateRequest;
import com.example.backend.payment.dto.PaymentResponseDto;
import com.example.backend.payment.entity.Payment;
import com.example.backend.payment.entity.PaymentStatus;
import com.example.backend.payment.repository.PaymentRepository;
import com.example.backend.payment.util.EcpayHashUtil;
import com.example.backend.shipment.service.ShipmentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final OrderService orderService;
    private final ShipmentService shipmentService;

    // --- 綠界測試帳號相關參數 ---
    @Value("${ecpay.merchantId}")
    private String merchantId;

    @Value("${ecpay.hashKey}")
    private String hashKey;

    @Value("${ecpay.hashIV}")
    private String hashIV;

    @Value("${ecpay.testUrl}")
    private String ecpayTestUrl;

    /*
     * =========================
     * 建立付款單
     * =========================
     */

    @Transactional
    public Long createPayment(PaymentCreateRequest request) {

        // 檢查訂單狀態
        Order order = orderService.getOrderEntity(request.getOrderId());

        if (!order.isPayable()) {
            throw new IllegalStateException("此訂單狀態無法建立付款");
        }

        // 檢查是否已有進行中的付款
        boolean hasPendingPayment = paymentRepository
                .existsByOrderIdAndPaymentStatus(
                        request.getOrderId(),
                        PaymentStatus.PENDING);

        if (hasPendingPayment) {
            throw new IllegalStateException("此訂單已有進行中的付款，請勿重複建立");
        }

        Payment payment = Payment.create(
                request.getOrderId(),
                request.getPaymentMethod(),
                request.getPaymentProvider());

        return paymentRepository.save(payment).getId();
    }

    /*
     * =========================
     * 模擬付款（原本流程）
     * =========================
     */

    @Transactional
    public PaymentResponseDto pay(Long paymentId) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("找不到付款紀錄"));

        if (payment.getPaymentStatus() == PaymentStatus.PAID) {
            throw new IllegalStateException("此付款已完成，請勿重複付款");
        }

        // 模擬交易編號
        String transactionId = UUID.randomUUID().toString();

        payment.markPaid(transactionId);

        // 更新訂單狀態
        orderService.markOrderPaid(payment.getOrderId());

        // 自動建立出貨單
        shipmentService.createShipment(payment.getOrderId(), "預設運送方式");

        return toResponse(payment);
    }

    /*
     * =========================
     * 綠界測試付款
     * =========================
     */
    @Transactional
    public String createECPayPaymentUrl(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("找不到付款紀錄"));

        Order order = orderService.getOrderEntity(payment.getOrderId());

        if (payment.getPaymentStatus() != PaymentStatus.PENDING) {
            throw new IllegalStateException("此付款狀態不可進行綠界付款");
        }

        String merchantTradeNo = "TEST" + System.currentTimeMillis();
        String merchantTradeDate = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));

        Map<String, String> params = new HashMap<>();
        params.put("MerchantID", merchantId);
        params.put("MerchantTradeNo", merchantTradeNo);
        params.put("MerchantTradeDate", merchantTradeDate);
        params.put("PaymentType", "aio");
        params.put("TotalAmount", order.getTotalAmount().toBigInteger().toString());
        params.put("TradeDesc", "測試訂單：" + order.getId());
        params.put("ItemName", "商品" + order.getId());
        params.put("ReturnURL", "https://ceremonious-achromic-catrice.ngrok-free.dev/api/payments/ecpay/callback");
        params.put("ChoosePayment", "Credit"); // 建議用 Credit 測試
        params.put("EncryptType", "1"); // SHA256

        // ➤ 產生 CheckMacValue
        String checkMacValue = EcpayHashUtil.generateCheckMacValue(params, hashKey, hashIV);
        params.put("CheckMacValue", checkMacValue);

        // 建立表單 HTML
        StringBuilder formBuilder = new StringBuilder();
        formBuilder.append("<form id=\"ecpay-form\" method=\"post\" action=\"")
                .append(ecpayTestUrl)
                .append("\">");

        for (Map.Entry<String, String> entry : params.entrySet()) {
            formBuilder.append("<input type=\"hidden\" name=\"")
                    .append(entry.getKey())
                    .append("\" value=\"")
                    .append(entry.getValue())
                    .append("\"/>");
        }

        formBuilder.append("</form>");
        formBuilder.append("<script>document.getElementById('ecpay-form').submit();</script>");

        // 更新 DB 記錄 merchantTradeNo
        payment.assignMerchantTradeNo(merchantTradeNo);
        paymentRepository.save(payment);

        return formBuilder.toString();
    }

    @Transactional
    public void processEcpayCallback(Map<String, String> params) {
        // TODO: 如果要正式上線：驗證 CheckMacValue

        String rtnCode = params.get("RtnCode");
        String merchantTradeNo = params.get("MerchantTradeNo");

        // 找對 Payment
        Payment payment = paymentRepository.findByMerchantTradeNo(merchantTradeNo)
                .orElseThrow(() -> new IllegalArgumentException("找不到付款資料"));

        Order order = orderRepository.findById(payment.getOrderId())
                .orElseThrow(() -> new IllegalArgumentException("訂單不存在"));

        if ("1".equals(rtnCode)) {
            payment.markSuccess();
            order.pay();
        } else {
            payment.markFailed();
            order.paymentFailed();
        }
    }

    /*
     * =========================
     * 查詢付款
     * =========================
     */

    @Transactional(readOnly = true)
    public List<PaymentResponseDto> getByOrderId(Long orderId) {

        return paymentRepository.findByOrderId(orderId).stream()
                .map(this::toResponse)
                .toList();
    }

    private PaymentResponseDto toResponse(Payment payment) {
        return PaymentResponseDto.builder()
                .paymentId(payment.getId())
                .orderId(payment.getOrderId())
                .paymentMethod(payment.getPaymentMethod().name())
                .paymentProvider(payment.getPaymentProvider())
                .paymentStatus(payment.getPaymentStatus().name())
                .transactionId(payment.getTransactionId())
                .paidAt(payment.getPaidAt())
                .build();
    }
}
