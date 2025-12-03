package com.example.backend.service;

import com.example.backend.entity.CategoryImage;
import com.example.backend.repository.CategoryImageRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryImageService {

    private final CategoryImageRepository categoryImageRepository;

    public CategoryImageService(CategoryImageRepository categoryImageRepository) {
        this.categoryImageRepository = categoryImageRepository;
    }

    // 查詢全部分類圖片
    public List<CategoryImage> getAllCategoryImages() {
        return categoryImageRepository.findAll();
    }

    // 根據 ID 查詢
    public Optional<CategoryImage> getCategoryImageById(Long id) {
        return categoryImageRepository.findById(id);
    }

    // 查詢某分類的所有圖片
    public List<CategoryImage> getImagesByCategoryId(Long categoryId) {
        return categoryImageRepository.findByCategoryId(categoryId);
    }

    // 新增或更新圖片
    public CategoryImage saveCategoryImage(CategoryImage image) {
        return categoryImageRepository.save(image);
    }

    // 刪除圖片
    public void deleteCategoryImage(Long id) {
        categoryImageRepository.deleteById(id);
    }
}
