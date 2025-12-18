package com.example.backend.order.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.order.dto.OrderResponseDTO;
import com.example.backend.order.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // @GetMapping
    // public List<OrderResponseDTO> getAllOrders() {
    // return orderService.getAllOrders();
    // }

    @GetMapping("/{orderId}")
    public OrderResponseDTO getOrderById(
            @PathVariable Long orderId,
            @RequestParam Long userId) {
        return orderService.getOrderById(orderId, userId);
    }

    /**
     * 正式下單流程
     */
    @PostMapping("/user/{userId}")
    public OrderResponseDTO createOrderFromCart(
            @PathVariable Long userId,
            @RequestParam String paymentMethod) {
        return orderService.createOrderFromCart(userId, paymentMethod);
    }
}
