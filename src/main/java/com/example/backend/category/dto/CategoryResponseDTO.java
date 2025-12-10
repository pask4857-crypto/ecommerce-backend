package com.example.backend.category.dto;

import lombok.Data;

@Data
public class CategoryResponseDTO {
    private Long categoryId;
    private String name;
    private String mainImage;
    private String description;
    private Boolean isActive;
    private Integer sortOrder;
}
