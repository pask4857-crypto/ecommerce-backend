package com.example.backend.product.dto;

import lombok.Data;

@Data
public class ProductImageResponseDTO {
    private Long imageId;
    private String imageUrl;
    private Long productId;
    private Integer sortOrder;
    private String altText;
}
