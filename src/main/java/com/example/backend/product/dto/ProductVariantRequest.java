package com.example.backend.product.dto;

import java.math.BigDecimal;
import lombok.Getter;

@Getter
public class ProductVariantRequest {
    private String sku;
    private String variantName;
    private BigDecimal price;
    private Integer stockQuantity;
}
