package com.example.backend.controller;

import com.example.backend.entity.ProductImage;
import com.example.backend.service.ProductImageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product-images")
@CrossOrigin(origins = "*")
public class ProductImageController {

    private final ProductImageService productImageService;

    public ProductImageController(ProductImageService productImageService) {
        this.productImageService = productImageService;
    }

    // 取得全部圖片
    @GetMapping
    public List<ProductImage> getAllImages() {
        return productImageService.getAllProductImages();
    }

    // 取得單一圖片
    @GetMapping("/{id}")
    public Optional<ProductImage> getImageById(@PathVariable Long id) {
        return productImageService.getProductImageById(id);
    }

    // 取得某商品的所有圖片
    @GetMapping("/product/{productId}")
    public List<ProductImage> getImagesByProductId(@PathVariable Long productId) {
        return productImageService.getImagesByProductId(productId);
    }

    // 新增圖片
    @PostMapping
    public ProductImage createImage(@RequestBody ProductImage image) {
        return productImageService.saveProductImage(image);
    }

    // 更新圖片
    @PutMapping("/{id}")
    public ProductImage updateImage(@PathVariable Long id, @RequestBody ProductImage image) {
        image.setImageId(id);
        return productImageService.saveProductImage(image);
    }

    // 刪除圖片
    @DeleteMapping("/{id}")
    public void deleteImage(@PathVariable Long id) {
        productImageService.deleteProductImage(id);
    }
}
