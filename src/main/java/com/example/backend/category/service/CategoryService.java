package com.example.backend.category.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.backend.category.dto.CategoryRequestDTO;
import com.example.backend.category.dto.CategoryResponseDTO;
import com.example.backend.category.entity.Category;
import com.example.backend.category.mapper.CategoryMapper;
import com.example.backend.category.repository.CategoryRepository;
import com.example.backend.common.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public List<CategoryResponseDTO> getAll() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    public CategoryResponseDTO getById(Long id) {
        Category entity = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + id));
        return categoryMapper.toDto(entity);
    }

    public CategoryResponseDTO create(CategoryRequestDTO dto) {
        Category entity = categoryMapper.toEntity(dto);
        return categoryMapper.toDto(categoryRepository.save(entity));
    }

    public CategoryResponseDTO update(Long id, CategoryRequestDTO dto) {
        Category entity = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + id));

        categoryMapper.updateEntityFromDto(dto, entity);
        return categoryMapper.toDto(categoryRepository.save(entity));
    }

    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category not found: " + id);
        }
        categoryRepository.deleteById(id);
    }
}
