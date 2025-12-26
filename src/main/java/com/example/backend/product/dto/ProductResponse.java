package com.example.backend.product.dto;

import com.example.backend.product.entity.ProductStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductResponse {
    private Long id;
    private String name;
    private ProductStatus status;
}
