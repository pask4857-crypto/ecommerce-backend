package com.example.backend.dto;

import lombok.Data;

@Data
public class ProductImageRequestDTO {
    private String imageUrl;
    private Long productId;
    private Integer sortOrder;
    private String altText;
}
