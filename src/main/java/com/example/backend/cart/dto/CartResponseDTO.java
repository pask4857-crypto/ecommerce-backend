package com.example.backend.cart.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class CartResponseDTO {

    private Long cartId;
    private Long userId;

    private Double totalAmount;
    private Double discountAmount;
    private Double finalAmount;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
