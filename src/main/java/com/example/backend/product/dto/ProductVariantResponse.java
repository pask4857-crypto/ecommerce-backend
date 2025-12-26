package com.example.backend.product.dto;

import com.example.backend.product.entity.ProductVariantStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductVariantResponse {
    private Long id;
    private String sku;
    private String variantName;
    private Integer stockQuantity;
    private ProductVariantStatus status;
}
