package com.example.backend.payment.entity;

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
@Table(name = "payments")
@Getter
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @Column(name = "payment_method", nullable = false)
    private String paymentMethod;

    @Column(name = "payment_provider", nullable = false)
    private String paymentProvider;

    @Column(name = "payment_status", nullable = false)
    private String paymentStatus;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "paid_at")
    private LocalDateTime paidAt;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    /*
     * =========================
     * Factory Method
     * =========================
     */
    public static Payment create(
            Long orderId,
            String paymentMethod,
            String paymentProvider) {
        Payment payment = new Payment();
        payment.orderId = orderId;
        payment.paymentMethod = paymentMethod;
        payment.paymentProvider = paymentProvider;
        payment.paymentStatus = "PENDING";
        payment.createdAt = LocalDateTime.now();
        return payment;
    }

    /*
     * =========================
     * Domain Behavior
     * =========================
     */

    public void markPaid(String transactionId) {
        if (!"PENDING".equals(this.paymentStatus)) {
            throw new IllegalStateException("付款狀態不正確，無法完成付款");
        }
        this.paymentStatus = "PAID";
        this.transactionId = transactionId;
        this.paidAt = LocalDateTime.now();
    }

    public void markFailed() {
        if (!"PENDING".equals(this.paymentStatus)) {
            throw new IllegalStateException("付款狀態不正確，無法標記失敗");
        }
        this.paymentStatus = "FAILED";
    }
}
