package com.example.backend.order.entity;

import java.math.BigDecimal;

import com.example.backend.cart.entity.CartItem;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_items")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long orderItemId;

    private Long orderId;
    private Long productId;
    private String productName;

    private Integer quantity;

    @Column(precision = 19, scale = 2)
    private BigDecimal price; // 下單當下單價

    @Column(precision = 19, scale = 2)
    private BigDecimal subtotal; // price * quantity

    /*
     * =================
     * Factory Method
     * =================
     */
    public static OrderItem fromCartItem(
            Long orderId,
            CartItem cartItem,
            String productName) {
        if (orderId == null) {
            throw new IllegalArgumentException("orderId must not be null");
        }
        if (cartItem == null) {
            throw new IllegalArgumentException("cartItem must not be null");
        }

        OrderItem item = new OrderItem();
        item.orderId = orderId;
        item.productId = cartItem.getProductId();
        item.productName = productName;
        item.quantity = cartItem.getQuantity();
        item.price = cartItem.getUnitPrice();
        item.calculateSubtotal();

        return item;
    }

    /*
     * =================
     * Internal Logic
     * =================
     */
    private void calculateSubtotal() {
        this.subtotal = price.multiply(BigDecimal.valueOf(quantity));
    }
}
