package com.example.backend.category.mapper;

import com.example.backend.category.dto.CategoryImageRequestDTO;
import com.example.backend.category.dto.CategoryImageResponseDTO;
import com.example.backend.category.entity.CategoryImage;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryImageMapper {

    CategoryImageMapper INSTANCE = Mappers.getMapper(CategoryImageMapper.class);

    CategoryImage toEntity(CategoryImageRequestDTO dto);

    CategoryImageResponseDTO toResponseDTO(CategoryImage entity);
}
