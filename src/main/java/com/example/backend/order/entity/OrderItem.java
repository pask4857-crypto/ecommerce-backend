package com.example.backend.order.entity;

import java.math.BigDecimal;

import com.example.backend.order.dto.OrderItemSnapshotDTO;

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
    public static OrderItem fromSnapshot(Long orderId, OrderItemSnapshotDTO snapshot) {
        if (orderId == null) {
            throw new IllegalArgumentException("orderId must not be null");
        }
        if (snapshot == null) {
            throw new IllegalArgumentException("snapshot must not be null");
        }

        OrderItem item = new OrderItem();
        item.setOrderId(orderId);
        item.setProductId(snapshot.getProductId());
        item.setProductName(snapshot.getProductName());
        item.setQuantity(snapshot.getQuantity());
        item.setPrice(snapshot.getPrice());
        item.calculateSubtotal();

        return item;
    }

    /*
     * =================
     * Internal Logic
     * =================
     */
    public void calculateSubtotal() {
        if (price == null || quantity == null) {
            this.subtotal = BigDecimal.ZERO;
        } else {
            this.subtotal = price.multiply(BigDecimal.valueOf(quantity));
        }
    }
}
