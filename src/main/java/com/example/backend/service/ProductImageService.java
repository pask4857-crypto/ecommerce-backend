package com.example.backend.service;

import com.example.backend.entity.ProductImage;
import com.example.backend.repository.ProductImageRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductImageService {

    private final ProductImageRepository productImageRepository;

    public ProductImageService(ProductImageRepository productImageRepository) {
        this.productImageRepository = productImageRepository;
    }

    // 查詢全部商品圖片
    public List<ProductImage> getAllProductImages() {
        return productImageRepository.findAll();
    }

    // 根據 ID 查詢
    public Optional<ProductImage> getProductImageById(Long id) {
        return productImageRepository.findById(id);
    }

    // 查詢某商品的所有圖片
    public List<ProductImage> getImagesByProductId(Long productId) {
        return productImageRepository.findByProductId(productId);
    }

    // 新增或更新圖片
    public ProductImage saveProductImage(ProductImage image) {
        return productImageRepository.save(image);
    }

    // 刪除圖片
    public void deleteProductImage(Long id) {
        productImageRepository.deleteById(id);
    }
}
