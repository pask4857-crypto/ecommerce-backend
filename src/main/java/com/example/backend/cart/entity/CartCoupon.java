package com.example.backend.cart.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cart_coupons")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
}
