package com.example.backend.cart.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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

public class CartCouponResponseDTO {

    private Long couponUseId;
    private Long cartId;
    private String couponCode;
    private BigDecimal discountAmount;
    private BigDecimal minSpend;
    private Boolean isActive;
    private LocalDateTime appliedAt;
}
