package com.example.backend.mapper;

import com.example.backend.dto.CategoryImageRequestDTO;
import com.example.backend.dto.CategoryImageResponseDTO;
import com.example.backend.entity.CategoryImage;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryImageMapper {

    CategoryImageMapper INSTANCE = Mappers.getMapper(CategoryImageMapper.class);

    CategoryImage toEntity(CategoryImageRequestDTO dto);

    CategoryImageResponseDTO toResponseDTO(CategoryImage entity);
}
