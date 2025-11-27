package com.example.backend.repository;

import com.example.backend.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    // 可依需求新增方法，例如找某訂單的所有商品
}
