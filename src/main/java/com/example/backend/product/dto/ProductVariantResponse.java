package com.example.backend.product.dto;

import com.example.backend.product.entity.ProductVariant;
import com.example.backend.product.entity.ProductVariantStatus;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductVariantResponse {
    private Long id;
    private String sku;
    private String variantName;
    private Integer stockQuantity;
    private ProductVariantStatus status;

    public static ProductVariantResponse fromEntity(ProductVariant v) {
        return ProductVariantResponse.builder()
                .id(v.getId())
                .sku(v.getSku())
                .variantName(v.getVariantName())
                .stockQuantity(v.getStockQuantity())
                .status(v.getStatus())
                .build();
    }
}
