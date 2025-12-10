package com.example.backend.cart.dto;

import lombok.Data;

@Data
public class CartRequestDTO {
    private Long userId;
    private Double totalAmount;
    private Double discountAmount;
    private Double finalAmount;
}
