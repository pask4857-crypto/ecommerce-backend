package com.example.backend.cart.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CartCouponResponseDTO {

    private Long couponUseId;
    private Long cartId;
    private String couponCode;
    private Integer discountAmount;
    private Integer minSpend;
    private Boolean isActive;
    private LocalDateTime appliedAt;
}
