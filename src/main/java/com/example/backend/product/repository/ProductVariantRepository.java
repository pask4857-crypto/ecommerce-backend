package com.example.backend.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.backend.product.entity.ProductVariant;
import com.example.backend.product.entity.ProductVariantStatus;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {

    List<ProductVariant> findByProductId(Long productId);

    List<ProductVariant> findByStatus(ProductVariantStatus status);

    Optional<ProductVariant> findBySku(String sku);
}
