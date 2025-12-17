package com.example.backend.order.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.order.dto.OrderItemResponseDTO;
import com.example.backend.order.service.OrderItemService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/order-items")
@RequiredArgsConstructor
public class OrderItemController {

    private final OrderItemService orderItemService;

    @GetMapping("/order/{orderId}")
    public List<OrderItemResponseDTO> getItemsByOrderId(@PathVariable Long orderId) {
        return orderItemService.getItemsByOrderId(orderId);
    }
}
