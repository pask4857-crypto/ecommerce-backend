package com.example.backend.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.product.entity.ProductImage;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

    // 查詢某商品底下全部圖片
    List<ProductImage> findByProductId(Long productId);
}
