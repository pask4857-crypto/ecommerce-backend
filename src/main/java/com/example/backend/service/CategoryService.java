package com.example.backend.service;

import com.example.backend.dto.CategoryRequestDTO;
import com.example.backend.dto.CategoryResponseDTO;
import com.example.backend.entity.Category;
import com.example.backend.exception.ResourceNotFoundException;
import com.example.backend.mapper.CategoryMapper;
import com.example.backend.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
