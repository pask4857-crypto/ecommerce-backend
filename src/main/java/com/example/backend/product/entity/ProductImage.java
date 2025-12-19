package com.example.backend.product.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product_images")
@Getter
@Setter
@NoArgsConstructor
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long imageId;

    private String imageUrl;
    private Long productId;
    private Integer sortOrder;
    private String altText;

    /*
     * ==========
     * Factory
     * ==========
     */
    public static ProductImage create(
            Long productId,
            String imageUrl,
            Integer sortOrder,
            String altText) {
        if (productId == null) {
            throw new IllegalArgumentException("productId must not be null");
        }
        if (imageUrl == null || imageUrl.isBlank()) {
            throw new IllegalArgumentException("imageUrl must not be blank");
        }

        ProductImage image = new ProductImage();
        image.productId = productId;
        image.imageUrl = imageUrl;
        image.sortOrder = sortOrder != null ? sortOrder : 0;
        image.altText = altText;

        return image;
    }

    /*
     * ==========
     * 行為方法
     * ==========
     */
    public void changeSortOrder(int sortOrder) {
        if (sortOrder < 0) {
            throw new IllegalArgumentException("sortOrder must be >= 0");
        }
        this.sortOrder = sortOrder;
    }

    public void changeAltText(String altText) {
        this.altText = altText;
    }
}
