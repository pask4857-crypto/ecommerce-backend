package com.example.backend.payment.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaymentCreateRequest {

    private Long orderId;
    private String paymentMethod;
    private String paymentProvider;
}
