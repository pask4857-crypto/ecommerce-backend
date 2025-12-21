package com.example.backend.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.backend.product.entity.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

    List<ProductImage> findByProductIdOrderBySortOrderAsc(Long productId);
}
