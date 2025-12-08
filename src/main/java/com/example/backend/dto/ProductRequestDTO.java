package com.example.backend.dto;

import lombok.Data;

@Data
public class ProductRequestDTO {
    private String name;
    private String description;
    private String sku;
    private Double price;
    private Double discountPrice;
    private Integer stock;
    private Integer stockWarning;
    private Double weight;
    private Boolean isActive;
    private Long categoryId;
    private String mainImage;
}
