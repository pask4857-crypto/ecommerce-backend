package com.example.backend.order.entity;

import java.math.BigDecimal;
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
@Table(name = "orders")
@Getter
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    private Long userId;

    @Column(precision = 19, scale = 2)
    private BigDecimal totalAmount;

    @Column(precision = 19, scale = 2)
    private BigDecimal discountAmount;

    @Column(precision = 19, scale = 2)
    private BigDecimal finalAmount;

    private String status;
    private String paymentMethod;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /*
     * =========================
     * Factory Method
     * =========================
     */
    public static Order createFromCart(
            Long userId,
            BigDecimal totalAmount,
            BigDecimal discountAmount,
            String paymentMethod) {
        if (totalAmount == null || totalAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("totalAmount must be >= 0");
        }

        if (discountAmount == null || discountAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("discountAmount must be >= 0");
        }

        Order order = new Order();
        order.userId = userId;
        order.totalAmount = totalAmount;
        order.discountAmount = discountAmount;
        order.finalAmount = order.calculateFinalAmount();
        order.status = "PENDING";
        order.paymentMethod = paymentMethod;
        order.createdAt = LocalDateTime.now();
        order.updatedAt = LocalDateTime.now();

        return order;
    }

    /*
     * =========================
     * Internal Logic
     * =========================
     */
    private BigDecimal calculateFinalAmount() {
        BigDecimal result = totalAmount.subtract(discountAmount);
        return result.compareTo(BigDecimal.ZERO) < 0
                ? BigDecimal.ZERO
                : result;
    }
}
