package com.example.backend.mapper;

import com.example.backend.dto.ProductImageRequestDTO;
import com.example.backend.dto.ProductImageResponseDTO;
import com.example.backend.entity.ProductImage;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProductImageMapper {

    ProductImage toEntity(ProductImageRequestDTO dto);

    ProductImageResponseDTO toDto(ProductImage entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(ProductImageRequestDTO dto, @MappingTarget ProductImage entity);
}
