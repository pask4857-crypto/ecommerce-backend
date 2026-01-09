package com.example.backend.payment.dto;

import java.time.LocalDateTime;

import com.example.backend.payment.entity.Payment;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaymentResponseDto {

    private Long paymentId;
    private Long orderId;
    private String paymentMethod;
    private String paymentProvider;
    private String paymentStatus;
    private String transactionId;
    private LocalDateTime paidAt;

    public static PaymentResponseDto fromEntity(Payment payment) {
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
