package com.example.backend.cart.entity;

import java.math.BigDecimal;

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
    @Column(name = "cart_item_id")
    private Long cartItemId;

    private Long cartId;
    private Long productId;

    private Integer quantity;

    @Column(precision = 19, scale = 2)
    private BigDecimal unitPrice; // 單價

    @Column(precision = 19, scale = 2)
    private BigDecimal totalPrice; // unitPrice * quantity

    /*
     * ==========
     * Factory
     * ==========
     */
    public static CartItem create(
            Long cartId,
            Long productId,
            Integer quantity,
            BigDecimal unitPrice) {
        if (cartId == null) {
            throw new IllegalArgumentException("cartId must not be null");
        }
        if (productId == null) {
            throw new IllegalArgumentException("productId must not be null");
        }
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("quantity must be > 0");
        }
        if (unitPrice == null || unitPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("unitPrice must be >= 0");
        }

        CartItem item = new CartItem();
        item.cartId = cartId;
        item.productId = productId;
        item.quantity = quantity;
        item.unitPrice = unitPrice;
        item.recalculateTotalPrice();

        return item;
    }

    /*
     * ==========
     * 行為方法
     * ==========
     */
    public void changeQuantity(Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("quantity must be > 0");
        }
        this.quantity = quantity;
        recalculateTotalPrice();
    }

    private void recalculateTotalPrice() {
        this.totalPrice = unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
}
