package com.example.backend.dto;

import lombok.Data;

@Data
public class OrderItemRequest {
    private Long productId; // 對應 Product ID
    private Integer quantity; // 購買數量
}
