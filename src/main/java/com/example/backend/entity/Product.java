package com.example.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
}
