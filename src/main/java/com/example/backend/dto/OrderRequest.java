package com.example.backend.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private String name;
    private String phone;
    private String address;
    private List<OrderItemRequest> items; // 購物車明細
}
