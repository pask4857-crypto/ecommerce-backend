package com.example.backend.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.backend.order.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
