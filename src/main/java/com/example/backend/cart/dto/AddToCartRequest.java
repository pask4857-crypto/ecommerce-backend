package com.example.backend.cart.dto;

import lombok.Getter;

@Getter
public class AddToCartRequest {
    private Long productVariantId;
    private Integer quantity;
}
