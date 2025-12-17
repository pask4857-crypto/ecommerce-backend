package com.example.backend.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRequestDTO {
    private Long orderId;
    private Long productId;
    private String productName;
    private Integer quantity;
    private Double price;
    private Double subtotal;
}
