package com.example.backend.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    private Long productId;
    private String name;
    private String description;
    private Double price;
    private Integer stock;
    private String mainImage;
    private Long categoryId;
}
