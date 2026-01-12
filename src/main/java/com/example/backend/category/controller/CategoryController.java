package com.example.backend.category.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.backend.category.dto.CategoryRenameRequest;
import com.example.backend.category.dto.CategoryResponse;
import com.example.backend.category.service.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * 建立分類
     */
    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(
            @RequestParam String name,
            @RequestParam String slug,
            @RequestParam(required = false) String description) {

        CategoryResponse dto = categoryService.createCategory(name, slug, description);
        return ResponseEntity.ok(dto);
    }

    /**
     * 查全部分類
     */
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        List<CategoryResponse> dtos = categoryService.getCategories();
        return ResponseEntity.ok(dtos);
    }

    /**
     * 查活動分類（ACTIVE）
     */
    @GetMapping("/active")
    public ResponseEntity<List<CategoryResponse>> getActiveCategories() {
        List<CategoryResponse> dtos = categoryService.getActiveCategories();
        return ResponseEntity.ok(dtos);
    }

    /**
     * 單筆查詢
     */
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategory(
            @PathVariable Long id) {
        CategoryResponse dto = categoryService.getCategory(id);
        return ResponseEntity.ok(dto);
    }

    @PatchMapping("/{id}/rename")
    public ResponseEntity<CategoryResponse> rename(
            @PathVariable Long id,
            @RequestBody CategoryRenameRequest request) {
        CategoryResponse response = categoryService.rename(id, request.getName(), request.getSlug());
        return ResponseEntity.ok(response);
    }

    /**
     * 修改分類名稱/描述
     */
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(
            @PathVariable Long id,
            @RequestParam String name,
            @RequestParam(required = false) String description) {

        CategoryResponse dto = categoryService.updateCategory(id, name, description);
        return ResponseEntity.ok(dto);
    }

    /**
     * 停用分類
     */
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivateCategory(
            @PathVariable Long id) {
        categoryService.deactivateCategory(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * 啟用分類
     */
    @PutMapping("/{id}/activate")
    public ResponseEntity<Void> activateCategory(
            @PathVariable Long id) {
        categoryService.activateCategory(id);
        return ResponseEntity.noContent().build();
    }

}
