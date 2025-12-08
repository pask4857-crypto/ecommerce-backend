package com.example.backend.dto;

import lombok.Data;

@Data
public class CategoryRequestDTO {
    private String name;
    private String mainImage;
    private String description;
    private Boolean isActive;
    private Integer sortOrder;
}
