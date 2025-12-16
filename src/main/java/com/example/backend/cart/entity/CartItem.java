package com.example.backend.cart.entity;

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
@Table(name = "cart_items")
@Getter
@Setter
@NoArgsConstructor // (access = AccessLevel.PROTECTED)
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Long cartItemId;

    private Long cartId;
    private Long productId;

    private Integer quantity;
    private Integer unitPrice;
    private Integer totalPrice;

    /*
     * ======================
     * Factory Method
     * ======================
     */

    public static CartItem create(
            Long cartId,
            Long productId,
            Integer quantity,
            Integer unitPrice) {
        CartItem item = new CartItem();
        item.cartId = cartId;
        item.productId = productId;
        item.quantity = quantity;
        item.unitPrice = unitPrice;
        item.totalPrice = unitPrice * quantity;
        return item;
    }

    /*
     * ======================
     * Domain Behavior（可選）
     * ======================
     */

    public void changeQuantity(Integer quantity) {
        this.quantity = quantity;
        this.totalPrice = this.unitPrice * quantity;
    }
}
