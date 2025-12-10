package com.example.backend.category.dto;

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

public class CategoryImageResponseDTO {
    private Long imageId;
    private String imageUrl;
    private Long categoryId;
    private Integer sortOrder;
    private String altText;
}
