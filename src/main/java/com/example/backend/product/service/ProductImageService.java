package com.example.backend.product.service;

import com.example.backend.common.exception.ResourceNotFoundException;
import com.example.backend.product.dto.ProductImageRequestDTO;
import com.example.backend.product.dto.ProductImageResponseDTO;
import com.example.backend.product.entity.ProductImage;
import com.example.backend.product.mapper.ProductImageMapper;
import com.example.backend.product.repository.ProductImageRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductImageService {

    private final ProductImageRepository productImageRepository;
    private final ProductImageMapper productImageMapper;

    public List<ProductImageResponseDTO> getImagesByProduct(Long productId) {
        return productImageRepository.findByProductId(productId)
                .stream()
                .map(productImageMapper::toDto)
                .toList();
    }

    public ProductImageResponseDTO getById(Long id) {
        ProductImage entity = productImageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProductImage not found: " + id));
        return productImageMapper.toDto(entity);
    }

    public ProductImageResponseDTO create(ProductImageRequestDTO dto) {
        ProductImage entity = productImageMapper.toEntity(dto);
        return productImageMapper.toDto(productImageRepository.save(entity));
    }

    public ProductImageResponseDTO update(Long id, ProductImageRequestDTO dto) {
        ProductImage entity = productImageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProductImage not found: " + id));

        productImageMapper.updateEntityFromDto(dto, entity);

        return productImageMapper.toDto(productImageRepository.save(entity));
    }

    public void delete(Long id) {
        if (!productImageRepository.existsById(id)) {
            throw new ResourceNotFoundException("ProductImage not found: " + id);
        }
        productImageRepository.deleteById(id);
    }
}
