package com.example.backend.shipment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.backend.shipment.entity.Shipment;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {

    List<Shipment> findByOrderId(Long orderId);
}
