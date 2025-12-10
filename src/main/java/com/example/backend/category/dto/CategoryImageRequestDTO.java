package com.example.backend.category.dto;

import lombok.Data;

@Data
public class CategoryImageRequestDTO {
    private String imageUrl;
    private Long categoryId;
    private Integer sortOrder;
    private String altText;
}
