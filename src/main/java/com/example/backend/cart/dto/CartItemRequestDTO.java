package com.example.backend.cart.dto;

import lombok.Data;

@Data
public class CartItemRequestDTO {

    private Long cartId;
    private Long productId;
    private Integer quantity;
    private Integer unitPrice; // 加入時的商品單價
}
