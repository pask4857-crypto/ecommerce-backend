package com.example.backend.category.dto;

import lombok.Data;

@Data
public class CategoryRequestDTO {
    private String name;
    private String mainImage;
    private String description;
    private Boolean isActive;
    private Integer sortOrder;
}
