package com.example.backend.order.entity;

import com.example.backend.cart.entity.CartItem;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "order_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    private Long orderId;
    private Long productId;
    private String productName;
    private Integer quantity;
    private Integer price; // 單價
    private Integer subtotal; // price * quantity

    public static OrderItem fromCartItem(
            Long orderId,
            CartItem cartItem,
            String productName) {
        return OrderItem.builder()
                .orderId(orderId)
                .productId(cartItem.getProductId())
                .productName(productName)
                .quantity(cartItem.getQuantity())
                .price(cartItem.getUnitPrice())
                .subtotal(cartItem.getTotalPrice())
                .build();
    }
}
