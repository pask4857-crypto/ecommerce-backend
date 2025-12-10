package com.example.backend.cart.dto;

import lombok.Data;

@Data
public class CartCouponRequestDTO {

    private Long cartId;
    private String couponCode;
    private Integer discountAmount;
    private Integer minSpend;
}
