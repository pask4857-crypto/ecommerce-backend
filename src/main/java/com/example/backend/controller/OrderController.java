package com.example.backend.controller;

import com.example.backend.dto.OrderRequest;
import com.example.backend.dto.OrderResponse;
import com.example.backend.entity.Order;
import com.example.backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173") // 對前端開放
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public OrderResponse placeOrder(@RequestBody OrderRequest request) {
        Order order = orderService.placeOrder(request);

        // 計算總金額
        int totalPrice = order.getItems()
                .stream()
                .mapToInt(item -> item.getPrice() * item.getQuantity())
                .sum();

        return new OrderResponse(order.getOrderId(), order.getName(), totalPrice);
    }
}
