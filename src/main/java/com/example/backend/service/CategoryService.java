package com.example.backend.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.backend.dto.CategoryDTO;
import com.example.backend.entity.Category;
import com.example.backend.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    // DTO → Entity
    public Category toEntity(CategoryDTO dto) {
        return Category.builder()
                .categoryId(dto.getCategoryId())
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
    }

    // Entity → DTO
    public CategoryDTO toDTO(Category category) {
        return CategoryDTO.builder()
                .categoryId(category.getCategoryId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }

    // CRUD
    public List<CategoryDTO> getAll() {
        return categoryRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<CategoryDTO> getById(Long id) {
        return categoryRepository.findById(id).map(this::toDTO);
    }

    public CategoryDTO save(CategoryDTO dto) {
        Category saved = categoryRepository.save(toEntity(dto));
        return toDTO(saved);
    }

    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}
