package com.example.backend.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CartItemResponse {
    private Long cartItemId;
    private Long productVariantId;
    private Integer quantity;
}
