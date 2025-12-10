package com.example.backend.category.controller;

import com.example.backend.category.dto.CategoryImageRequestDTO;
import com.example.backend.category.dto.CategoryImageResponseDTO;
import com.example.backend.category.service.CategoryImageService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category-images")
public class CategoryImageController {

    private final CategoryImageService categoryImageService;

    public CategoryImageController(CategoryImageService categoryImageService) {
        this.categoryImageService = categoryImageService;
    }

    @PostMapping
    public CategoryImageResponseDTO create(@RequestBody CategoryImageRequestDTO dto) {
        return categoryImageService.create(dto);
    }

    @PutMapping("/{id}")
    public CategoryImageResponseDTO update(
            @PathVariable Long id,
            @RequestBody CategoryImageRequestDTO dto) {
        return categoryImageService.update(id, dto);
    }

    @GetMapping("/{id}")
    public CategoryImageResponseDTO getById(@PathVariable Long id) {
        return categoryImageService.getById(id);
    }

    @GetMapping("/category/{categoryId}")
    public List<CategoryImageResponseDTO> getByCategory(@PathVariable Long categoryId) {
        return categoryImageService.getByCategory(categoryId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        categoryImageService.delete(id);
    }
}
