package com.example.backend.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResponseDTO {
    private Long cartItemId;
    private Long cartId;
    private Long productId;
    private Integer quantity;
    private Integer unitPrice;
    private Integer totalPrice;
}
