package com.example.backend.order.entity;

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
import lombok.Setter;

@Entity
@Table(name = "order_items")
@Getter
@Setter
@NoArgsConstructor // (access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long orderItemId;

    private Long orderId;
    private Long productId;

    private String productName;

    private Integer quantity;

    private Integer price; // 下單當下單價（快照）
    private Integer subtotal; // price * quantity

    /*
     * ======================
     * Factory Method
     * ======================
     */

    public static OrderItem fromCartItem(Long orderId, CartItem cartItem) {

        OrderItem item = new OrderItem();
        item.orderId = orderId;
        item.productId = cartItem.getProductId();
        item.quantity = cartItem.getQuantity();
        item.price = cartItem.getUnitPrice();
        item.subtotal = cartItem.getTotalPrice();

        // ⚠️ 如果你目前沒有 productName，可先留 null
        item.productName = null;

        return item;
    }
}
