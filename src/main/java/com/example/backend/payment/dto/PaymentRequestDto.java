package com.example.backend.payment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequestDto {

    private Long orderId;
    private String paymentMethod;
    private String paymentProvider;
}
