package com.example.backend.shipment.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.backend.shipment.dto.ShipmentResponseDto;
import com.example.backend.shipment.entity.Shipment;
import com.example.backend.shipment.repository.ShipmentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShipmentService {

    private final ShipmentRepository shipmentRepository;

    @Transactional
    public ShipmentResponseDto createShipment(Long orderId, String shippingMethod) {
        Shipment shipment = Shipment.create(orderId, shippingMethod);
        shipment = shipmentRepository.save(shipment);
        return toResponse(shipment);
    }

    @Transactional
    public ShipmentResponseDto markShipped(Long shipmentId, String trackingNumber) {
        Shipment shipment = shipmentRepository.findById(shipmentId)
                .orElseThrow(() -> new IllegalArgumentException("找不到出貨單"));
        shipment.markShipped(trackingNumber);
        return toResponse(shipment);
    }

    @Transactional
    public ShipmentResponseDto markDelivered(Long shipmentId) {
        Shipment shipment = shipmentRepository.findById(shipmentId)
                .orElseThrow(() -> new IllegalArgumentException("找不到出貨單"));
        shipment.markDelivered();
        return toResponse(shipment);
    }

    @Transactional(readOnly = true)
    public List<ShipmentResponseDto> getByOrderId(Long orderId) {
        return shipmentRepository.findByOrderId(orderId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private ShipmentResponseDto toResponse(Shipment shipment) {
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
