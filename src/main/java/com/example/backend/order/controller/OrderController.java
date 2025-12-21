package com.example.backend.order.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.order.dto.OrderCreateRequest;
import com.example.backend.order.dto.OrderCreateResponse;
import com.example.backend.order.dto.OrderDetailResponse;
import com.example.backend.order.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

        private final OrderService orderService;

        @PostMapping
        public ResponseEntity<OrderCreateResponse> createOrder(
                        @RequestBody OrderCreateRequest request) {
                Long orderId = orderService.placeOrder(
                                request.getUserId(),
                                request.getShippingAddress());

                return ResponseEntity.ok(
                                new OrderCreateResponse(orderId, "訂單建立成功"));
        }

        @GetMapping("/{orderId}")
        public ResponseEntity<OrderDetailResponse> getOrderDetail(
                        @PathVariable Long orderId) {
                return ResponseEntity.ok(
                                orderService.getOrderDetail(orderId));
        }

        @GetMapping
        public ResponseEntity<List<OrderDetailResponse>> getOrdersByUser(
                        @RequestParam Long userId) {
                return ResponseEntity.ok(
                                orderService.getOrdersByUser(userId));
        }

}
