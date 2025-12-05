package com.example.backend.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductImageDTO {
    private Long imageId;
    private Long productId;
    private String imageUrl;
    private String altText;
    private Integer sortOrder;
}
