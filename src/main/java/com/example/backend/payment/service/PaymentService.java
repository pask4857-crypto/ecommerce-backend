package com.example.backend.payment.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.backend.payment.dto.PaymentRequestDto;
import com.example.backend.payment.dto.PaymentResponseDto;
import com.example.backend.payment.entity.Payment;
import com.example.backend.payment.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Transactional
    public PaymentResponseDto createPayment(PaymentRequestDto request) {

        Payment payment = Payment.create(
                request.getOrderId(),
                request.getPaymentMethod(),
                request.getPaymentProvider());

        paymentRepository.save(payment);

        return toResponse(payment);
    }

    @Transactional
    public PaymentResponseDto pay(Long paymentId) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("找不到付款紀錄"));

        // 模擬第三方金流交易編號
        String transactionId = UUID.randomUUID().toString();

        payment.markPaid(transactionId);

        return toResponse(payment);
    }

    public PaymentResponseDto getByOrderId(Long orderId) {

        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new IllegalArgumentException("找不到該訂單的付款資訊"));

        return toResponse(payment);
    }

    private PaymentResponseDto toResponse(Payment payment) {
        return PaymentResponseDto.builder()
                .paymentId(payment.getId())
                .orderId(payment.getOrderId())
                .paymentMethod(payment.getPaymentMethod())
                .paymentProvider(payment.getPaymentProvider())
                .paymentStatus(payment.getPaymentStatus())
                .transactionId(payment.getTransactionId())
                .paidAt(payment.getPaidAt())
                .build();
    }
}
