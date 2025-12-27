package com.example.backend.shipment.dto;

import lombok.Getter;

@Getter
public class ShipmentCreateRequest {
    private Long orderId;
    private String shippingMethod;
}
