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

public class CartCouponRequestDTO {

    private Long cartId;
    private String couponCode;
    private Integer discountAmount;
    private Integer minSpend;
}
