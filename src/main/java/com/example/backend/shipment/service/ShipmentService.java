package com.example.backend.shipment.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.backend.shipment.dto.ShipmentResponseDto;
import com.example.backend.shipment.entity.Shipment;
import com.example.backend.shipment.entity.ShipmentStatus;
import com.example.backend.shipment.repository.ShipmentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShipmentService {

    private final ShipmentRepository shipmentRepository;

    @Transactional
    public ShipmentResponseDto createShipment(Long orderId, String shippingMethod) {

        // 如果已經建立過，回傳 DTO
        Optional<Shipment> existing = shipmentRepository.findByOrderId(orderId);
        if (existing.isPresent()) {
            return ShipmentResponseDto.fromEntity(existing.get());
        }

        Shipment shipment = Shipment.create(orderId, shippingMethod);
        shipment = shipmentRepository.save(shipment);

        Shipment saved = shipmentRepository.save(shipment);
        return ShipmentResponseDto.fromEntity(saved);
    }

    @Transactional
    public ShipmentResponseDto markShipped(Long shipmentId) {
        Shipment shipment = shipmentRepository.findById(shipmentId)
                .orElseThrow(() -> new IllegalArgumentException("出貨單不存在"));

        if (shipment.getStatus() != ShipmentStatus.PENDING) {
            throw new IllegalStateException("出貨單已經標記為已出貨或已完成");
        }

        // 自動生成 tracking number（範例：SHIP+當前時間戳+亂數）
        String trackingNumber = "SHIP" + System.currentTimeMillis() + (int) (Math.random() * 1000);

        shipment.markShipped(trackingNumber); // 這裡更新狀態與追蹤號碼
        shipmentRepository.save(shipment);

        return ShipmentResponseDto.fromEntity(shipment);
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
