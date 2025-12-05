package com.example.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dto.CategoryImageDTO;
import com.example.backend.service.CategoryImageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/category-images")
@RequiredArgsConstructor
public class CategoryImageController {

    private final CategoryImageService imageService;

    @GetMapping
    public List<CategoryImageDTO> getAll() {
        return imageService.getAll();
    }

    @GetMapping("/{id}")
    public CategoryImageDTO getById(@PathVariable Long id) {
        return imageService.getById(id).orElse(null);
    }

    @GetMapping("/category/{categoryId}")
    public List<CategoryImageDTO> getByCategory(@PathVariable Long categoryId) {
        return imageService.getByCategory(categoryId);
    }

    @PostMapping
    public CategoryImageDTO create(@RequestBody CategoryImageDTO dto) {
        return imageService.save(dto);
    }

    @PutMapping("/{id}")
    public CategoryImageDTO update(@PathVariable Long id, @RequestBody CategoryImageDTO dto) {
        dto.setImageId(id);
        return imageService.save(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        imageService.delete(id);
    }
}
