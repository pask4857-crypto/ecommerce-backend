package com.example.backend.product.entity;

import java.math.BigDecimal;
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
    private String sku;

    // 💰 金額統一 BigDecimal
    private BigDecimal price;
    private BigDecimal discountPrice;

    private Integer stock;
    private Integer stockWarning;
    private Double weight;

    private Boolean isActive;
    private Long categoryId;
    private String mainImage;

    private Integer sales;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /*
     * ==========
     * Factory
     * ==========
     */
    public static Product create(
            String name,
            String description,
            String sku,
            BigDecimal price,
            BigDecimal discountPrice,
            Integer stock,
            Integer stockWarning,
            Double weight,
            Long categoryId,
            String mainImage) {
        Product product = new Product();
        product.name = name;
        product.description = description;
        product.sku = sku;
        product.price = price;
        product.discountPrice = discountPrice;
        product.stock = stock;
        product.stockWarning = stockWarning;
        product.weight = weight;
        product.categoryId = categoryId;
        product.mainImage = mainImage;

        product.isActive = true;
        product.sales = 0;
        product.createdAt = LocalDateTime.now();
        product.updatedAt = LocalDateTime.now();

        return product;
    }

    /*
     * ==========
     * 行為方法
     * ==========
     */
    public void deactivate() {
        this.isActive = false;
        this.updatedAt = LocalDateTime.now();
    }

    public void activate() {
        this.isActive = true;
        this.updatedAt = LocalDateTime.now();
    }

    public void increaseSales(int quantity) {
        this.sales += quantity;
    }

    public void decreaseStock(int quantity) {
        if (this.stock < quantity) {
            throw new IllegalStateException("Insufficient stock");
        }
        this.stock -= quantity;
    }
}
