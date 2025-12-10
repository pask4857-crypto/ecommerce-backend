package com.example.backend.product.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product_images")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long imageId;
    private String imageUrl;
    private Long productId; // 商品ID
    private Integer sortOrder; // 顯示順序
    private String altText; // 圖片描述

    public ProductImage(String imageUrl, Long productId, Integer sortOrder, String altText) {
        this.imageUrl = imageUrl;
        this.productId = productId;
        this.sortOrder = sortOrder;
        this.altText = altText;
    }
}
