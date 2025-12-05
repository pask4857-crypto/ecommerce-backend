package com.example.backend.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.backend.dto.ProductDTO;
import com.example.backend.entity.Product;
import com.example.backend.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    // DTO → Entity
    public Product toEntity(ProductDTO dto) {
        return Product.builder()
                .productId(dto.getProductId())
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .mainImage(dto.getMainImage())
                .categoryId(dto.getCategoryId())
                .build();
    }

    // Entity → DTO
    public ProductDTO toDTO(Product product) {
        return ProductDTO.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .mainImage(product.getMainImage())
                .categoryId(product.getCategoryId())
                .build();
    }

    // CRUD
    public List<ProductDTO> getAll() {
        return productRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<ProductDTO> getById(Long id) {
        return productRepository.findById(id).map(this::toDTO);
    }

    public List<ProductDTO> getByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ProductDTO save(ProductDTO dto) {
        Product saved = productRepository.save(toEntity(dto));
        return toDTO(saved);
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
