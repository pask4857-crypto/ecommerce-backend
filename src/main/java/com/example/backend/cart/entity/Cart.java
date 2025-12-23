package com.example.backend.cart.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cart")
@Getter
@NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public static Cart create(Long userId) {
        Cart cart = new Cart();
        cart.userId = userId;
        cart.createdAt = LocalDateTime.now();
        cart.updatedAt = LocalDateTime.now();
        return cart;
    }

    public void touch() {
        this.updatedAt = LocalDateTime.now();
    }
}
