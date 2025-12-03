package com.example.backend.service;

import com.example.backend.entity.Product;
import com.example.backend.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // 查詢全部商品
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // 根據 ID 查詢商品
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    // 根據分類查詢商品
    public List<Product> getProductsByCategoryId(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    // 新增或更新商品
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    // 刪除商品
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
