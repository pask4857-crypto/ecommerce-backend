package com.example.backend.product.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.example.backend.product.dto.ProductImageRequestDTO;
import com.example.backend.product.dto.ProductImageResponseDTO;
import com.example.backend.product.entity.ProductImage;

@Mapper(componentModel = "spring")
public interface ProductImageMapper {

    ProductImage toEntity(ProductImageRequestDTO dto);

    ProductImageResponseDTO toDto(ProductImage entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(ProductImageRequestDTO dto, @MappingTarget ProductImage entity);
}
