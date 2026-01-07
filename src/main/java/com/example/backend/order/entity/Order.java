package com.example.backend.order.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_number", nullable = false, unique = true)
    private String orderNumber;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column(name = "total_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "shipping_address", nullable = false)
    private String shippingAddress;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    /*
     * =========================
     * Factory Method
     * =========================
     */

    public static Order create(
            String orderNumber,
            Long userId,
            BigDecimal totalAmount,
            String shippingAddress) {
        Order order = new Order();
        order.orderNumber = orderNumber;
        order.userId = userId;
        order.status = OrderStatus.CREATED;
        order.totalAmount = totalAmount;
        order.shippingAddress = shippingAddress;
        order.createdAt = LocalDateTime.now();
        order.updatedAt = LocalDateTime.now();
        return order;
    }

    /*
     * =========================
     * Domain Methods
     * =========================
     */

    public boolean isPayable() {
        return this.status == OrderStatus.CREATED;
    }

    public void markPaid() {
        if (!isPayable()) {
            throw new IllegalStateException("只有 CREATED 狀態的訂單可以付款");
        }
        this.status = OrderStatus.PAID;
        this.updatedAt = LocalDateTime.now();
    }

    public void cancel() {
        this.status = OrderStatus.CANCELLED;
        this.updatedAt = LocalDateTime.now();
    }

    public void pay() {
        this.status = OrderStatus.PAID;
        this.updatedAt = LocalDateTime.now();
    }

    public void paymentFailed() {
        this.status = OrderStatus.FAILED;
        this.updatedAt = LocalDateTime.now();
    }
}
