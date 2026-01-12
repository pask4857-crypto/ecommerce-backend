package com.example.backend.category.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryRenameRequest {
    private String name;
    private String slug;
}
