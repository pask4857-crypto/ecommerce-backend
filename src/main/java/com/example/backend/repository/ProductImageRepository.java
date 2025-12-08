package com.example.backend.repository;

import com.example.backend.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

    // 查詢某商品底下全部圖片
    List<ProductImage> findByProductId(Long productId);
}
