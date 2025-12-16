package com.example.backend.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderRequestDTO {
    private Long userId;
    private Double totalAmount;
    private Double discountAmount;
    private Double finalAmount;
    private String status;
    private String paymentMethod;
}
