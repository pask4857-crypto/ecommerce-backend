package com.example.backend.product.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

    @Column(nullable = false)
    private String status;

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
        variant.status = "ACTIVE";
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
        this.status = "INACTIVE";
        this.updatedAt = LocalDateTime.now();
    }
}
