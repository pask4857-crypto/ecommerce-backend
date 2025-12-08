package com.example.backend.dto;

import lombok.Data;

@Data
public class CategoryImageRequestDTO {
    private String imageUrl;
    private Long categoryId;
    private Integer sortOrder;
    private String altText;
}
