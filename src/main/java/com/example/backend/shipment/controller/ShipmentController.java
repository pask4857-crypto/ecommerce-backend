package com.example.backend.shipment.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.shipment.dto.ShipmentCreateRequest;
import com.example.backend.shipment.dto.ShipmentResponseDto;
import com.example.backend.shipment.service.ShipmentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/shipments")
@RequiredArgsConstructor
public class ShipmentController {

        private final ShipmentService shipmentService;

        @PostMapping
        public ResponseEntity<ShipmentResponseDto> createShipment(
                        @RequestBody ShipmentCreateRequest request) {
                ShipmentResponseDto dto = shipmentService.createShipment(
                                request.getOrderId(),
                                request.getShippingMethod());
                return ResponseEntity.ok(dto);
        }

        @PostMapping("/{shipmentId}/shipped")
        public ResponseEntity<ShipmentResponseDto> markShipped(@PathVariable Long shipmentId) {
                ShipmentResponseDto dto = shipmentService.markShipped(shipmentId);
                return ResponseEntity.ok(dto);
        }

        @PostMapping("/{shipmentId}/delivered")
        public ResponseEntity<ShipmentResponseDto> markDelivered(
                        @PathVariable Long shipmentId) {
                ShipmentResponseDto dto = shipmentService.markDelivered(shipmentId);
                return ResponseEntity.ok(dto);
        }

        @GetMapping("/order/{orderId}")
        public ResponseEntity<List<ShipmentResponseDto>> getByOrderId(
                        @PathVariable Long orderId) {
                return ResponseEntity.ok(shipmentService.getByOrderId(orderId));
        }
}
