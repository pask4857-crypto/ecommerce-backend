package com.example.backend.product.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class ProductRequestDTO {
    private String name;
    private String description;
    private String sku;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private Integer stock;
    private Integer stockWarning;
    private Double weight;
    private Boolean isActive;
    private Long categoryId;
    private String mainImage;
}
