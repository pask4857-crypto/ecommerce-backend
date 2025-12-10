package com.example.backend.category.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "category_images")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long imageId;
    private String imageUrl;
    private Long categoryId; // 分類ID
    private Integer sortOrder; // 顯示順序
    private String altText; // 圖片描述
}
