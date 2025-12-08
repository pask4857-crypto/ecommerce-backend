package com.example.backend.controller;

import com.example.backend.dto.CategoryRequestDTO;
import com.example.backend.dto.CategoryResponseDTO;
import com.example.backend.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryResponseDTO> getAll() {
        return categoryService.getAll();
    }

    @GetMapping("/{id}")
    public CategoryResponseDTO getById(@PathVariable Long id) {
        return categoryService.getById(id);
    }

    @PostMapping
    public CategoryResponseDTO create(@RequestBody CategoryRequestDTO dto) {
        return categoryService.create(dto);
    }

    @PutMapping("/{id}")
    public CategoryResponseDTO update(@PathVariable Long id, @RequestBody CategoryRequestDTO dto) {
        return categoryService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        categoryService.delete(id);
    }
}
