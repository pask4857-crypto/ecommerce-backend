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
@Table(name = "carts")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long cartId;
    private Long userId; // 使用者 ID
    private Double totalAmount; // 商品總金額
    private Double discountAmount; // 折扣金額
    private Double finalAmount; // 結算後金額
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Cart(Long userId, Double totalAmount, Double discountAmount, Double finalAmount, LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.discountAmount = discountAmount;
        this.finalAmount = finalAmount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
