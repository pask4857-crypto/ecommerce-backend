package com.example.backend.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.backend.entity.Product;
import com.example.backend.entity.ProductImage;
import com.example.backend.repository.ProductImageRepository;
import com.example.backend.repository.ProductRepository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;

    // 取得所有產品，附帶主要圖片檔名
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

    // 新增商品
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Product createProductWithImage(
            @RequestPart("product") Product product,
            @RequestPart("image") MultipartFile image) {
        try {
            // 先儲存商品
            Product savedProduct = productRepository.save(product);

            // 處理圖片（如果有傳）
            if (image != null && !image.isEmpty()) {
                // 存檔的檔名：避免衝突用時間戳
                String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
                Path uploadPath = Paths.get("uploads/product-images/" + fileName);

                // 寫入實體硬碟
                Files.copy(image.getInputStream(), uploadPath);

                // 建立 ProductImage 物件
                ProductImage pi = new ProductImage();
                pi.setProduct(savedProduct);
                pi.setImageUrl(fileName);
                pi.setIsMain(true);

                productImageRepository.save(pi);
            }

            return savedProduct;

        } catch (Exception e) {
            throw new RuntimeException("圖片上傳失敗", e);
        }
    }

    // 更新商品
    @PutMapping("/{id}")
    public Product updateProduct(
            @PathVariable Long id,
            @RequestBody Product updated) {
        return productRepository.findById(id).map(p -> {
            p.setName(updated.getName());
            p.setPrice(updated.getPrice());
            p.setDescription(updated.getDescription());
            return productRepository.save(p);
        }).orElseThrow(() -> new RuntimeException("商品不存在"));
    }

    // 刪除商品
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
    }

    // 更新圖片
    @PostMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String updateProductImage(
            @PathVariable Long id,
            @RequestPart("image") MultipartFile image) {
        try {
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("商品不存在"));

            // 建立新檔名
            String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
            Path uploadPath = Paths.get("uploads/product-images/" + fileName);
            Files.copy(image.getInputStream(), uploadPath);

            // 找到主圖
            ProductImage mainImg = productImageRepository.findByProductAndIsMain(product, true);

            if (mainImg == null) {
                mainImg = new ProductImage();
                mainImg.setProduct(product);
                mainImg.setIsMain(true);
            }

            mainImg.setImageUrl(fileName);
            productImageRepository.save(mainImg);

            return "OK";

        } catch (Exception e) {
            throw new RuntimeException("更新圖片失敗", e);
        }
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
