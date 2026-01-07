package com.example.backend.payment.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.payment.dto.PaymentCreateRequest;
import com.example.backend.payment.dto.PaymentResponseDto;
import com.example.backend.payment.service.PaymentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    /**
     * 建立付款單（PENDING）
     */
    @PostMapping
    public ResponseEntity<Long> createPayment(
            @RequestBody PaymentCreateRequest request) {

        return ResponseEntity.ok(paymentService.createPayment(request));
    }

    /**
     * 模擬付款成功
     */
    @PostMapping("/{paymentId}/pay")
    public ResponseEntity<PaymentResponseDto> pay(
            @PathVariable Long paymentId) {
        return ResponseEntity.ok(paymentService.pay(paymentId));
    }

    /**
     * 取得綠界付款網址（回傳 URL 字串）
     */
    @PostMapping("/{paymentId}/ecpay")
    public ResponseEntity<String> getEcpayPaymentUrl(
            @PathVariable Long paymentId) {

        String paymentUrl = paymentService.createECPayPaymentUrl(paymentId);
        return ResponseEntity.ok(paymentUrl);
    }

    /**
     * 查詢訂單付款狀態
     */
    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<PaymentResponseDto>> getByOrderId(
            @PathVariable Long orderId) {

        return ResponseEntity.ok(paymentService.getByOrderId(orderId));
    }
}
