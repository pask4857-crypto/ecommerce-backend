package com.example.backend.shipment.entity;

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
@Table(name = "shipments")
@Getter
@NoArgsConstructor
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @Column(name = "shipping_method", nullable = false)
    private String shippingMethod;

    @Column(name = "tracking_number")
    private String trackingNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ShipmentStatus status;

    @Column(name = "shipped_at")
    private LocalDateTime shippedAt;

    @Column(name = "delivered_at")
    private LocalDateTime deliveredAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /*
     * =========================
     * Factory Method
     * =========================
     */
    public static Shipment create(Long orderId, String shippingMethod) {
        Shipment shipment = new Shipment();
        shipment.orderId = orderId;
        shipment.shippingMethod = shippingMethod;
        shipment.status = ShipmentStatus.PENDING;
        shipment.updatedAt = LocalDateTime.now();
        return shipment;
    }

    /*
     * =========================
     * Domain Methods
     * =========================
     */
    public void markShipped(String trackingNumber) {
        if (this.status != ShipmentStatus.PENDING) {
            throw new IllegalStateException("只有 PENDING 狀態的出貨單可以標記為已出貨");
        }
        this.status = ShipmentStatus.SHIPPED;
        this.trackingNumber = trackingNumber;
        this.shippedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void markDelivered() {
        if (this.status != ShipmentStatus.SHIPPED) {
            throw new IllegalStateException("只有 SHIPPED 狀態的出貨單可以標記為已送達");
        }
        this.status = ShipmentStatus.DELIVERED;
        this.deliveredAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void cancel() {
        if (this.status == ShipmentStatus.DELIVERED) {
            throw new IllegalStateException("已送達的出貨單不可取消");
        }
        this.status = ShipmentStatus.CANCELLED;
        this.updatedAt = LocalDateTime.now();
    }
}
