package com.example.backend.cart.dto;

import java.math.BigDecimal;

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
    private BigDecimal discountAmount;
    private BigDecimal minSpend;
}
