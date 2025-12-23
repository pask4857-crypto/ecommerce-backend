package com.example.backend.payment.dto;

import java.time.LocalDateTime;

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
}
