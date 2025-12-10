package com.example.backend.product.mapper;

import com.example.backend.product.dto.ProductRequestDTO;
import com.example.backend.product.dto.ProductResponseDTO;
import com.example.backend.product.entity.Product;

import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toEntity(ProductRequestDTO dto);

    ProductResponseDTO toDto(Product entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(ProductRequestDTO dto, @MappingTarget Product entity);
}
