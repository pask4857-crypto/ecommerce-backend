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
@Table(name = "cart_items")
@Getter
@NoArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cart_id", nullable = false)
    private Long cartId;

    @Column(name = "product_variant_id", nullable = false)
    private Long productVariantId;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public static CartItem create(Long cartId, Long productVariantId, int quantity) {
        CartItem item = new CartItem();
        item.cartId = cartId;
        item.productVariantId = productVariantId;
        item.quantity = quantity;
        item.createdAt = LocalDateTime.now();
        item.updatedAt = LocalDateTime.now();
        return item;
    }

    public void increaseQuantity(int amount) {
        this.quantity += amount;
        this.updatedAt = LocalDateTime.now();
    }
}
