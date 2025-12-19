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
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor

public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;
    private String name;
    private String mainImage;
    private String description; // 分類描述
    private Boolean isActive; // 上架/下架狀態
    private Integer sortOrder; // 顯示排序

    public Category(String name, String mainImage, String description, Boolean isActive, Integer sortOrder) {
        this.name = name;
        this.mainImage = mainImage;
        this.description = description;
        this.isActive = isActive;
        this.sortOrder = sortOrder;
    }
}
