package com.example.backend.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDTO {
    private Long userId;
    private Integer totalAmount;
    private Integer discountAmount;
    private Integer finalAmount;
    private String status;
    private String paymentMethod;
}
