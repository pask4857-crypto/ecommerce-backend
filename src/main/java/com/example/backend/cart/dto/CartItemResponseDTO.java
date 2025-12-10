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

public class CartItemResponseDTO {

    private Long cartItemId;
    private Long cartId;
    private Long productId;
    private Integer quantity;
    private Integer unitPrice;
    private Integer totalPrice; // 小計
}
