package com.example.backend.order.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.order.dto.OrderItemRequestDTO;
import com.example.backend.order.dto.OrderItemResponseDTO;
import com.example.backend.order.service.OrderItemService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/order-items")
@RequiredArgsConstructor
public class OrderItemController {

    private final OrderItemService orderItemService;

    @GetMapping("/order/{orderId}")
    public List<OrderItemResponseDTO> getByOrder(@PathVariable Long orderId) {
        return orderItemService.getByOrderId(orderId);
    }

    @PostMapping
    public OrderItemResponseDTO create(@RequestBody OrderItemRequestDTO dto) {
        return orderItemService.create(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        orderItemService.delete(id);
    }
}
