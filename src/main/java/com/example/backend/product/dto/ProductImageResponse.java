package com.example.backend.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductImageResponse {
    private String imageUrl;
    private Integer sortOrder;
}
