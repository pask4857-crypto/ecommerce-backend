package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
}
