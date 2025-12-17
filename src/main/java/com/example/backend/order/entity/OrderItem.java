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
@NoArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long orderItemId;

    private Long orderId;
    private Long productId;
    private String productName;
    private Integer quantity;
    private Double price; // 下單當下商品單價
    private Double subtotal; // price * quantity

    public static OrderItem fromCartItem(Long orderId, CartItem cartItem, String productName) {
        OrderItem item = new OrderItem();
        item.orderId = orderId;
        item.productId = cartItem.getProductId();
        item.productName = productName;
        item.quantity = cartItem.getQuantity();
        item.price = cartItem.getUnitPrice().doubleValue();
        item.subtotal = cartItem.getTotalPrice().doubleValue();
        return item;
    }
}
