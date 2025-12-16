package com.example.backend.order.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.order.dto.OrderResponseDTO;
import com.example.backend.order.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * 查詢全部訂單（管理 / 測試用）
     */
    @GetMapping
    public List<OrderResponseDTO> getAll() {
        return orderService.getAllOrders();
    }

    /**
     * 查詢單一訂單
     */
    @GetMapping("/{id}")
    public OrderResponseDTO getById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    /**
     * 使用購物車正式下單
     */
    @PostMapping("/from-cart/{userId}")
    public OrderResponseDTO createFromCart(@PathVariable Long userId) {
        return orderService.createOrderFromCart(userId);
    }
}
