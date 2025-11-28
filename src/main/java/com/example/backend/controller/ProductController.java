package com.example.backend.controller;

import com.example.backend.entity.Product;
import com.example.backend.entity.ProductImage;
import com.example.backend.repository.ProductImageRepository;
import com.example.backend.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;

    // 取得所有產品，附帶主要圖片 URL
    @GetMapping
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();

        return products.stream().map(product -> {
            ProductImage mainImage = productImageRepository.findByProductAndIsMain(product, true);
            String imageUrl = mainImage != null
                    ? "http://localhost:8080/uploads/product-images/" + mainImage.getImageUrl()
                    : null;

            return new ProductResponse(
                    product.getProductId(),
                    product.getName(),
                    product.getPrice(),
                    product.getDescription(),
                    imageUrl);
        }).collect(Collectors.toList());
    }

    // 新增產品（測試用）
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    // DTO
    @Data
    @AllArgsConstructor
    static class ProductResponse {
        private Long productId;
        private String name;
        private Integer price;
        private String description;
        private String image;
    }
}
