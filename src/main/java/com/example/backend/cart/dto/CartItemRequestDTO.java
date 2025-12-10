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

public class CartItemRequestDTO {

    private Long cartId;
    private Long productId;
    private Integer quantity;
    private Integer unitPrice; // 加入時的商品單價
}
