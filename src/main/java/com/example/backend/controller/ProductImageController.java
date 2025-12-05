package com.example.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dto.ProductImageDTO;
import com.example.backend.service.ProductImageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/product-images")
@RequiredArgsConstructor
public class ProductImageController {

    private final ProductImageService imageService;

    @GetMapping
    public List<ProductImageDTO> getAll() {
        return imageService.getAll();
    }

    @GetMapping("/{id}")
    public ProductImageDTO getById(@PathVariable Long id) {
        return imageService.getById(id).orElse(null);
    }

    @GetMapping("/product/{productId}")
    public List<ProductImageDTO> getByProduct(@PathVariable Long productId) {
        return imageService.getByProduct(productId);
    }

    @PostMapping
    public ProductImageDTO create(@RequestBody ProductImageDTO dto) {
        return imageService.save(dto);
    }

    @PutMapping("/{id}")
    public ProductImageDTO update(@PathVariable Long id, @RequestBody ProductImageDTO dto) {
        dto.setImageId(id);
        return imageService.save(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        imageService.delete(id);
    }
}
