package com.example.backend.product.dto;

import lombok.Getter;

@Getter
public class ProductImageRequest {
    private String imageUrl;
    private Integer sortOrder;
}
