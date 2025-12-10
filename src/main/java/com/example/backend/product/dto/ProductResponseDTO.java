package com.example.backend.product.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ProductResponseDTO {
    private Long productId;
    private String name;
    private String description;
    private String sku;
    private Double price;
    private Double discountPrice;
    private Integer stock;
    private Integer stockWarning;
    private Double weight;
    private Boolean isActive;
    private Long categoryId;
    private String mainImage;
    private Integer sales;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
