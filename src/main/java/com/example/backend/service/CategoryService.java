package com.example.backend.service;

import com.example.backend.entity.Category;
import com.example.backend.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // 查詢全部分類
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // 根據 ID 查詢
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    // 新增或更新分類
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    // 刪除分類
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
