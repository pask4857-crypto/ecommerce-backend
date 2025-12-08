package com.example.backend.dto;

import lombok.Data;

@Data
public class CategoryImageResponseDTO {
    private Long imageId;
    private String imageUrl;
    private Long categoryId;
    private Integer sortOrder;
    private String altText;
}
