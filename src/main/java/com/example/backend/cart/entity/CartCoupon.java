package com.example.backend.cart.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cart_coupons")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class CartCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_use_id")
    private Long couponUseId;
    private Long cartId;
    private String couponCode;
    private Integer discountAmount; // 折抵金額
    private Integer minSpend; // 最低消費
    private Boolean isActive;
    private LocalDateTime appliedAt;

    public CartCoupon(Long cartId, String couponCode, Integer discountAmount, Integer minSpend, Boolean isActive,
            LocalDateTime appliedAt) {
        this.cartId = cartId;
        this.couponCode = couponCode;
        this.discountAmount = discountAmount;
        this.minSpend = minSpend;
        this.isActive = isActive;
        this.appliedAt = appliedAt;
    }
}
