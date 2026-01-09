package com.example.backend.payment.entity;

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
    private PaymentMethod paymentMethod;

    @Column(name = "payment_provider", nullable = false)
    private String paymentProvider;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    private PaymentStatus paymentStatus;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "merchant_trade_no")
    private String merchantTradeNo;

    @Column(name = "paid_at")
    private LocalDateTime paidAt;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

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
        payment.paymentMethod = PaymentMethod.Credit;
        payment.paymentProvider = paymentProvider;
        payment.paymentStatus = PaymentStatus.PENDING;
        payment.createdAt = LocalDateTime.now();
        payment.updatedAt = LocalDateTime.now();
        return payment;
    }

    /*
     * =========================
     * Domain Behavior
     * =========================
     */

    public void assignMerchantTradeNo(String merchantTradeNo) {
        if (this.paymentStatus == PaymentStatus.PAID) {
            throw new IllegalStateException("已付款的交易不能重新設定 MerchantTradeNo");
        }
        this.merchantTradeNo = merchantTradeNo;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isPaid() {
        return this.paymentStatus == PaymentStatus.PAID;
    }

    public void markPaid(String transactionId) {
        if (this.paymentStatus != PaymentStatus.PENDING) {
            throw new IllegalStateException("只有 PENDING 狀態的付款可以完成");
        }
        this.paymentStatus = PaymentStatus.PAID;
        this.transactionId = transactionId;
        this.paidAt = LocalDateTime.now();
    }

    public void markSuccess() {
        this.paymentStatus = PaymentStatus.PAID;
        this.updatedAt = LocalDateTime.now();
    }

    public void markFailed() {
        this.paymentStatus = PaymentStatus.FAILED;
        this.updatedAt = LocalDateTime.now();
    }

    public void markPending() {
        this.paymentStatus = PaymentStatus.PENDING;
        this.updatedAt = LocalDateTime.now();
    }

}
