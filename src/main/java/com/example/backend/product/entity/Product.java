package com.example.backend.product.entity;

import java.time.LocalDateTime;

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
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;
    private String name;
    private String description;
    private String sku; // 商品編號
    private Double price;
    private Double discountPrice; // 折扣價
    private Integer stock;
    private Integer stockWarning; // 庫存警示值
    private Double weight; // 商品重量
    private Boolean isActive; // 上架/下架狀態
    private Long categoryId; // 分類ID
    private String mainImage;
    private Integer sales; // 銷售量
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Product(String name, String description, String sku, Double price, Double discountPrice, Integer stock,
            Integer stockWarning, Double weight, Boolean isActive, Long categoryId, String mainImage, Integer sales,
            LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.name = name;
        this.description = description;
        this.sku = sku;
        this.price = price;
        this.discountPrice = discountPrice;
        this.stock = stock;
        this.stockWarning = stockWarning;
        this.weight = weight;
        this.isActive = isActive;
        this.categoryId = categoryId;
        this.mainImage = mainImage;
        this.sales = sales;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
