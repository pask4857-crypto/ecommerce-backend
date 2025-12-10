package com.example.backend.product.dto;

import lombok.Data;

@Data
public class ProductImageRequestDTO {
    private String imageUrl;
    private Long productId;
    private Integer sortOrder;
    private String altText;
}
