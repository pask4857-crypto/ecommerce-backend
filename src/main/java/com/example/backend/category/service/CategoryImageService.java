package com.example.backend.category.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.backend.category.dto.CategoryImageRequestDTO;
import com.example.backend.category.dto.CategoryImageResponseDTO;
import com.example.backend.category.entity.CategoryImage;
import com.example.backend.category.mapper.CategoryImageMapper;
import com.example.backend.category.repository.CategoryImageRepository;
import com.example.backend.common.exception.ResourceNotFoundException;

@Service
public class CategoryImageService {

    private final CategoryImageRepository categoryImageRepository;
    private final CategoryImageMapper categoryImageMapper;

    public CategoryImageService(
            CategoryImageRepository categoryImageRepository,
            CategoryImageMapper categoryImageMapper) {
        this.categoryImageRepository = categoryImageRepository;
        this.categoryImageMapper = categoryImageMapper;
    }

    public CategoryImageResponseDTO create(CategoryImageRequestDTO dto) {
        CategoryImage entity = categoryImageMapper.toEntity(dto);
        CategoryImage saved = categoryImageRepository.save(entity);
        return categoryImageMapper.toResponseDTO(saved);
    }

    public CategoryImageResponseDTO update(Long id, CategoryImageRequestDTO dto) {
        CategoryImage entity = categoryImageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category image not found with id: " + id));

        entity.setImageUrl(dto.getImageUrl());
        entity.setCategoryId(dto.getCategoryId());
        entity.setSortOrder(dto.getSortOrder());
        entity.setAltText(dto.getAltText());

        CategoryImage saved = categoryImageRepository.save(entity);
        return categoryImageMapper.toResponseDTO(saved);
    }

    public CategoryImageResponseDTO getById(Long id) {
        CategoryImage entity = categoryImageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category image not found with id: " + id));
        return categoryImageMapper.toResponseDTO(entity);
    }

    public List<CategoryImageResponseDTO> getByCategory(Long categoryId) {
        return categoryImageRepository.findByCategoryId(categoryId)
                .stream()
                .map(categoryImageMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public void delete(Long id) {
        categoryImageRepository.deleteById(id);
    }
}
