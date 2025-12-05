package com.example.backend.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.backend.dto.ProductImageDTO;
import com.example.backend.entity.ProductImage;
import com.example.backend.repository.ProductImageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductImageService {

    private final ProductImageRepository productImageRepository;

    // DTO → Entity
    public ProductImage toEntity(ProductImageDTO dto) {
        return ProductImage.builder()
                .imageId(dto.getImageId())
                .productId(dto.getProductId())
                .imageUrl(dto.getImageUrl())
                .altText(dto.getAltText())
                .sortOrder(dto.getSortOrder())
                .build();
    }

    // Entity → DTO
    public ProductImageDTO toDTO(ProductImage img) {
        return ProductImageDTO.builder()
                .imageId(img.getImageId())
                .productId(img.getProductId())
                .imageUrl(img.getImageUrl())
                .altText(img.getAltText())
                .sortOrder(img.getSortOrder())
                .build();
    }

    // CRUD
    public List<ProductImageDTO> getAll() {
        return productImageRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<ProductImageDTO> getById(Long id) {
        return productImageRepository.findById(id).map(this::toDTO);
    }

    public List<ProductImageDTO> getByProduct(Long productId) {
        return productImageRepository.findByProductId(productId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ProductImageDTO save(ProductImageDTO dto) {
        ProductImage saved = productImageRepository.save(toEntity(dto));
        return toDTO(saved);
    }

    public void delete(Long id) {
        productImageRepository.deleteById(id);
    }
}
