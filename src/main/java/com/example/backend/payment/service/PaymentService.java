package com.example.backend.payment.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.backend.order.entity.Order;
import com.example.backend.order.service.OrderService;
import com.example.backend.payment.dto.PaymentCreateRequest;
import com.example.backend.payment.dto.PaymentResponseDto;
import com.example.backend.payment.entity.Payment;
import com.example.backend.payment.entity.PaymentStatus;
import com.example.backend.payment.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderService orderService;

    @Transactional
    public Long createPayment(PaymentCreateRequest request) {

        // ⭐ 一定要先檢查訂單狀態
        Order order = orderService.getOrderEntity(request.getOrderId());

        if (!order.isPayable()) {
            throw new IllegalStateException("此訂單狀態無法建立付款");
        }

        // 2️⃣ 檢查是否已有進行中的付款（Payment enum）
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

    @Transactional
    public PaymentResponseDto pay(Long paymentId) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("找不到付款紀錄"));

        if ("PAID".equals(payment.getPaymentStatus())) {
            throw new IllegalStateException("此付款已完成，請勿重複付款");
        }

        String transactionId = UUID.randomUUID().toString();

        payment.markPaid(transactionId);

        // ⭐ 付款成功 → 更新訂單狀態
        orderService.markOrderPaid(payment.getOrderId());

        return toResponse(payment);
    }

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
                .paymentMethod(payment.getPaymentMethod())
                .paymentProvider(payment.getPaymentProvider())
                .paymentStatus(payment.getPaymentStatus().name())
                .transactionId(payment.getTransactionId())
                .paidAt(payment.getPaidAt())
                .build();
    }
}
