package com.example.backend.controller;

import com.example.backend.dto.ProductImageRequestDTO;
import com.example.backend.dto.ProductImageResponseDTO;
import com.example.backend.service.ProductImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-images")
@RequiredArgsConstructor
public class ProductImageController {

    private final ProductImageService productImageService;

    // 查詢某商品全部圖片
    @GetMapping("/product/{productId}")
    public List<ProductImageResponseDTO> getByProduct(@PathVariable Long productId) {
        return productImageService.getImagesByProduct(productId);
    }

    @GetMapping("/{id}")
    public ProductImageResponseDTO getById(@PathVariable Long id) {
        return productImageService.getById(id);
    }

    @PostMapping
    public ProductImageResponseDTO create(@RequestBody ProductImageRequestDTO dto) {
        return productImageService.create(dto);
    }

    @PutMapping("/{id}")
    public ProductImageResponseDTO update(@PathVariable Long id, @RequestBody ProductImageRequestDTO dto) {
        return productImageService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productImageService.delete(id);
    }
}
