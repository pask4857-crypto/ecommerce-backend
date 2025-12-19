package com.example.backend.category.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "category_images")
@Getter
@Setter
@NoArgsConstructor

public class CategoryImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long imageId;
    private String imageUrl;
    private Long categoryId; // 分類ID
    private Integer sortOrder; // 顯示順序
    private String altText; // 圖片描述

    public CategoryImage(String imageUrl, Long categoryId, Integer sortOrder, String altText) {
        this.imageUrl = imageUrl;
        this.categoryId = categoryId;
        this.sortOrder = sortOrder;
        this.altText = altText;
    }
}
