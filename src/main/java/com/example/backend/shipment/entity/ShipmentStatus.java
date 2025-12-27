package com.example.backend.shipment.entity;

public enum ShipmentStatus {
    PENDING, // 尚未出貨
    SHIPPED, // 已出貨
    DELIVERED, // 已送達
    CANCELLED; // 已取消
}
