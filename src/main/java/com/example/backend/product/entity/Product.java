package com.example.backend.product.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Getter
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductStatus status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    /*
     * =========================
     * Factory Method
     * =========================
     */

    public static Product create(String name, String description) {
        Product product = new Product();
        product.name = name;
        product.description = description;
        product.status = ProductStatus.DRAFT;
        product.createdAt = LocalDateTime.now();
        product.updatedAt = LocalDateTime.now();
        return product;
    }

    /*
     * =========================
     * Domain Methods（預留）
     * =========================
     */

    public void updateBasicInfo(String name, String description) {
        this.name = name;
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }

    public void activate() {
        if (this.status != ProductStatus.DRAFT
                && this.status != ProductStatus.INACTIVE) {
            throw new IllegalStateException("目前狀態不可上架");
        }
        this.status = ProductStatus.ACTIVE;
        this.updatedAt = LocalDateTime.now();
    }

    public void archive() {
        if (this.status == ProductStatus.ARCHIVED) {
            throw new IllegalStateException("商品已封存");
        }
        this.status = ProductStatus.ARCHIVED;
    }

    public void deactivate() {
        this.status = ProductStatus.INACTIVE;
        this.updatedAt = LocalDateTime.now();
    }
}
