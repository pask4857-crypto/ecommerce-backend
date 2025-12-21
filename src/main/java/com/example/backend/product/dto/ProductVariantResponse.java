package com.example.backend.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductVariantResponse {
    private Long id;
    private String sku;
    private String variantName;
    private Integer stockQuantity;
    private String status;
}
