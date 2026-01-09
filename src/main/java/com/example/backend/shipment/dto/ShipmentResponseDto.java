package com.example.backend.shipment.dto;

import java.time.LocalDateTime;

import com.example.backend.shipment.entity.Shipment;
import com.example.backend.shipment.entity.ShipmentStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ShipmentResponseDto {

    private Long shipmentId;
    private Long orderId;
    private String shippingMethod;
    private String trackingNumber;
    private ShipmentStatus status;
    private LocalDateTime shippedAt;
    private LocalDateTime deliveredAt;

    public static ShipmentResponseDto fromEntity(Shipment shipment) {
        return ShipmentResponseDto.builder()
                .shipmentId(shipment.getId())
                .orderId(shipment.getOrderId())
                .shippingMethod(shipment.getShippingMethod())
                .trackingNumber(shipment.getTrackingNumber())
                .status(shipment.getStatus())
                .shippedAt(shipment.getShippedAt())
                .deliveredAt(shipment.getDeliveredAt())
                .build();
    }
}
