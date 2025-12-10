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

public class CategoryRequestDTO {
    private String name;
    private String mainImage;
    private String description;
    private Boolean isActive;
    private Integer sortOrder;
}
