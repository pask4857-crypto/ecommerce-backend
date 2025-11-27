package com.example.backend.repository;

import com.example.backend.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // 之後可以新增依使用者、日期等查詢方法
}
