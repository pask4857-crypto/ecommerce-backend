package com.example.backend.product.entity;

import java.math.BigDecimal;
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
@Table(name = "product_variants")
@Getter
@NoArgsConstructor
public class ProductVariant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(nullable = false, unique = true)
    private String sku;

    @Column(name = "variant_name", nullable = false)
    private String variantName;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    @Column(name = "stock_quantity", nullable = false)
    private Integer stockQuantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductVariantStatus status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    /*
     * =========================
     * Factory Method
     * =========================
     */

    public static ProductVariant create(
            Long productId,
            String sku,
            String variantName,
            BigDecimal price,
            Integer stockQuantity) {
        ProductVariant variant = new ProductVariant();
        variant.productId = productId;
        variant.sku = sku;
        variant.variantName = variantName;
        variant.price = price;
        variant.stockQuantity = stockQuantity;
        variant.status = ProductVariantStatus.ACTIVE;
        variant.createdAt = LocalDateTime.now();
        variant.updatedAt = LocalDateTime.now();
        return variant;
    }

    /*
     * =========================
     * Domain Methods
     * =========================
     */

    public void adjustStock(int diff) {
        this.stockQuantity += diff;
        this.updatedAt = LocalDateTime.now();
    }

    public void deactivate() {
        this.status = ProductVariantStatus.DISABLED;
        this.updatedAt = LocalDateTime.now();
    }

    public void decreaseStock(int amount) {
        if (amount <= 0)
            throw new IllegalArgumentException("數量必須大於 0");
        if (this.stockQuantity < amount) {
            throw new IllegalStateException("庫存不足");
        }
        this.stockQuantity -= amount;

        // 庫存0時自動切換狀態
        if (this.stockQuantity == 0) {
            this.status = ProductVariantStatus.OUT_OF_STOCK;
        }
        this.updatedAt = LocalDateTime.now();
    }

    public void markOutOfStock() {
        if (this.status == ProductVariantStatus.OUT_OF_STOCK) {
            throw new IllegalStateException("此商品變體已是 OUT_OF_STOCK 狀態");
        }
        this.status = ProductVariantStatus.OUT_OF_STOCK;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isOutOfStock() {
        return this.status == ProductVariantStatus.OUT_OF_STOCK;
    }

    public void disable() {
        if (this.status == ProductVariantStatus.DISABLED) {
            throw new IllegalStateException("此商品變體已是停用狀態");
        }
        if (this.status == ProductVariantStatus.OUT_OF_STOCK) {
            throw new IllegalStateException("OUT_OF_STOCK 建議補庫存或手動評估後再停用");
        }
        this.status = ProductVariantStatus.DISABLED;
        this.updatedAt = LocalDateTime.now();
    }
}
