package com.example.backend.product.dto;

import com.example.backend.product.entity.Product;
import com.example.backend.product.entity.ProductStatus;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductResponse {
    private Long id;
    private String name;
    private ProductStatus status;

    public static ProductResponse fromEntity(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .status(product.getStatus())
                .build();
    }
}
