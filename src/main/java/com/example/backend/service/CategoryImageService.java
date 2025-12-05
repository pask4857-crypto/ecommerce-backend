package com.example.backend.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.backend.dto.CategoryImageDTO;
import com.example.backend.entity.CategoryImage;
import com.example.backend.repository.CategoryImageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryImageService {

    private final CategoryImageRepository categoryImageRepository;

    // DTO → Entity
    public CategoryImage toEntity(CategoryImageDTO dto) {
        return CategoryImage.builder()
                .imageId(dto.getImageId())
                .categoryId(dto.getCategoryId())
                .imageUrl(dto.getImageUrl())
                .altText(dto.getAltText())
                .sortOrder(dto.getSortOrder())
                .build();
    }

    // Entity → DTO
    public CategoryImageDTO toDTO(CategoryImage img) {
        return CategoryImageDTO.builder()
                .imageId(img.getImageId())
                .categoryId(img.getCategoryId())
                .imageUrl(img.getImageUrl())
                .altText(img.getAltText())
                .sortOrder(img.getSortOrder())
                .build();
    }

    // CRUD
    public List<CategoryImageDTO> getAll() {
        return categoryImageRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<CategoryImageDTO> getById(Long id) {
        return categoryImageRepository.findById(id).map(this::toDTO);
    }

    public List<CategoryImageDTO> getByCategory(Long categoryId) {
        return categoryImageRepository.findByCategoryId(categoryId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public CategoryImageDTO save(CategoryImageDTO dto) {
        CategoryImage img = categoryImageRepository.save(toEntity(dto));
        return toDTO(img);
    }

    public void delete(Long id) {
        categoryImageRepository.deleteById(id);
    }
}
