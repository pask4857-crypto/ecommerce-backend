package com.example.backend.product.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductDetailResponse {
    private Long id;
    private String name;
    private String description;
    private List<ProductVariantResponse> variants;
    private List<ProductImageResponse> images;
}
