package com.example.backend.product.dto;

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

public class ProductImageRequestDTO {
    private String imageUrl;
    private Long productId;
    private Integer sortOrder;
    private String altText;
}
