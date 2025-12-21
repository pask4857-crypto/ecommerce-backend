package com.example.backend.product.entity;

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
@Table(name = "product_images")
@Getter
@NoArgsConstructor
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "sort_order", nullable = false)
    private Integer sortOrder;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    /*
     * =========================
     * Factory Method
     * =========================
     */

    public static ProductImage create(
            Long productId,
            String imageUrl,
            Integer sortOrder) {
        ProductImage image = new ProductImage();
        image.productId = productId;
        image.imageUrl = imageUrl;
        image.sortOrder = sortOrder;
        image.createdAt = LocalDateTime.now();
        return image;
    }
}
