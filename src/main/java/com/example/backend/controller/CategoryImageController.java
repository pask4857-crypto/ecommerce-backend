package com.example.backend.controller;

import com.example.backend.entity.CategoryImage;
import com.example.backend.service.CategoryImageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/category-images")
@CrossOrigin(origins = "*")
public class CategoryImageController {

    private final CategoryImageService categoryImageService;

    public CategoryImageController(CategoryImageService categoryImageService) {
        this.categoryImageService = categoryImageService;
    }

    // 取得全部圖片
    @GetMapping
    public List<CategoryImage> getAllImages() {
        return categoryImageService.getAllCategoryImages();
    }

    // 取得單一圖片
    @GetMapping("/{id}")
    public Optional<CategoryImage> getImageById(@PathVariable Long id) {
        return categoryImageService.getCategoryImageById(id);
    }

    // 取得某分類的所有圖片
    @GetMapping("/category/{categoryId}")
    public List<CategoryImage> getImagesByCategoryId(@PathVariable Long categoryId) {
        return categoryImageService.getImagesByCategoryId(categoryId);
    }

    // 新增圖片
    @PostMapping
    public CategoryImage createImage(@RequestBody CategoryImage image) {
        return categoryImageService.saveCategoryImage(image);
    }

    // 更新圖片
    @PutMapping("/{id}")
    public CategoryImage updateImage(@PathVariable Long id, @RequestBody CategoryImage image) {
        image.setImageId(id);
        return categoryImageService.saveCategoryImage(image);
    }

    // 刪除圖片
    @DeleteMapping("/{id}")
    public void deleteImage(@PathVariable Long id) {
        categoryImageService.deleteCategoryImage(id);
    }
}
