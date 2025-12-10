package com.example.backend.cart.dto;

import lombok.Data;

@Data
public class CartItemResponseDTO {

    private Long cartItemId;
    private Long cartId;
    private Long productId;
    private Integer quantity;
    private Integer unitPrice;
    private Integer totalPrice; // 小計
}
