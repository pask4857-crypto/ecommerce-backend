package com.example.backend.cart.dto;

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

public class CartRequestDTO {
    private Long userId;
    private Double totalAmount;
    private Double discountAmount;
    private Double finalAmount;
}
