package com.example.backend.product.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.product.dto.ProductCreateRequest;
import com.example.backend.product.dto.ProductDetailResponse;
import com.example.backend.product.dto.ProductImageRequest;
import com.example.backend.product.dto.ProductResponse;
import com.example.backend.product.dto.ProductVariantRequest;
import com.example.backend.product.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Long> createProduct(
            @RequestBody ProductCreateRequest request) {
        return ResponseEntity.ok(productService.createProduct(request));
    }

    @PostMapping("/{productId}/variants")
    public ResponseEntity<Void> addVariant(
            @PathVariable Long productId,
            @RequestBody ProductVariantRequest request) {
        productService.addVariant(productId, request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{productId}/images")
    public ResponseEntity<Void> addImage(
            @PathVariable Long productId,
            @RequestBody ProductImageRequest request) {
        productService.addImage(productId, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDetailResponse> getProductDetail(
            @PathVariable Long productId) {
        return ResponseEntity.ok(productService.getProductDetail(productId));
    }

    @PostMapping("/{productId}/activate")
    public ResponseEntity<Void> activateProduct(
            @PathVariable Long productId) {
        productService.activateProduct(productId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{productId}/deactivate")
    public ResponseEntity<Void> deactivateProduct(
            @PathVariable Long productId) {
        productService.deactivateProduct(productId);
        return ResponseEntity.noContent().build();
    }
}
