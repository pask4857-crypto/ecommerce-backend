package com.example.backend.category.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.backend.category.dto.CategoryResponse;
import com.example.backend.category.entity.Category;
import com.example.backend.category.entity.CategoryStatus;
import com.example.backend.category.repository.CategoryRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryResponse createCategory(String name, String slug, String description) {
        if (categoryRepository.existsBySlug(slug)) {
            throw new IllegalArgumentException("Slug 已存在，不可重複");
        }

        Category category = Category.create(name, slug, description);
        categoryRepository.save(category);
        return CategoryResponse.fromEntity(category);
    }

    public List<CategoryResponse> getCategories() {
        return categoryRepository.findAll().stream()
                .map(CategoryResponse::fromEntity)
                .toList();
    }

    public List<CategoryResponse> getActiveCategories() {
        return categoryRepository.findByStatus(CategoryStatus.ACTIVE).stream()
                .map(CategoryResponse::fromEntity)
                .toList();
    }

    public CategoryResponse getCategory(Long id) {
        return categoryRepository.findById(id)
                .map(CategoryResponse::fromEntity)
                .orElseThrow(() -> new IllegalArgumentException("分類不存在"));
    }

    public CategoryResponse rename(Long id, String newName, String newSlug) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("分類不存在: " + id));

        if (!category.getSlug().equals(newSlug)
                && categoryRepository.existsBySlug(newSlug)) {
            throw new IllegalArgumentException("Slug 已存在，不可重複");
        }
        category.rename(newName, newSlug); // 直接呼叫你的 Domain Method
        return CategoryResponse.fromEntity(category);
    }

    public CategoryResponse updateCategory(Long id, String name, String description) {
        Category c = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("分類不存在"));
        c.update(name, description);
        return CategoryResponse.fromEntity(c);
    }

    public void activateCategory(Long id) {
        Category c = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("分類不存在"));
        c.activate();
    }

    public void deactivateCategory(Long id) {
        Category c = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("分類不存在"));
        c.deactivate();
    }

}
