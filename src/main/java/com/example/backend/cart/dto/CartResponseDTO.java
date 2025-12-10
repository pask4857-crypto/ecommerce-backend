package com.example.backend.cart.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CartResponseDTO {

    private Long cartId;
    private Long userId;

    private Double totalAmount;
    private Double discountAmount;
    private Double finalAmount;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
