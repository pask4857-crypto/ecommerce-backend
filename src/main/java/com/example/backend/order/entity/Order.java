package com.example.backend.order.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    private Long userId;
    private Integer totalAmount;
    private Integer discountAmount;
    private Integer finalAmount;
    private String status;
    private String paymentMethod;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static Order createFromCart(Long userId, int totalAmount, int discountAmount, String paymentMethod) {
        Order order = new Order();
        order.userId = userId;
        order.totalAmount = totalAmount;
        order.discountAmount = discountAmount;
        order.finalAmount = Math.max(totalAmount - discountAmount, 0);
        order.status = "PENDING";
        order.paymentMethod = paymentMethod;
        order.createdAt = LocalDateTime.now();
        order.updatedAt = LocalDateTime.now();
        return order;
    }
}
