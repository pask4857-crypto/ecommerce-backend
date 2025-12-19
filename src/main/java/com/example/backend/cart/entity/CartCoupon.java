package com.example.backend.cart.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cart_coupons")
@Getter
@Setter
@NoArgsConstructor

public class CartCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_use_id")
    private Long couponUseId;
    private Long cartId;
    private String couponCode;
    private BigDecimal discountAmount; // 折抵金額
    private BigDecimal minSpend; // 最低消費
    private Boolean isActive;
    private LocalDateTime appliedAt;

    public CartCoupon(Long cartId, String couponCode, BigDecimal discountAmount, BigDecimal minSpend, Boolean isActive,
            LocalDateTime appliedAt) {
        this.cartId = cartId;
        this.couponCode = couponCode;
        this.discountAmount = discountAmount;
        this.minSpend = minSpend;
        this.isActive = isActive;
        this.appliedAt = appliedAt;
    }
}
