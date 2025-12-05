package com.example.backend.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryImageDTO {
    private Long imageId;
    private Long categoryId;
    private String imageUrl;
    private String altText;
    private Integer sortOrder;
}
