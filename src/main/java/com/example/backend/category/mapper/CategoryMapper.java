package com.example.backend.category.mapper;

import com.example.backend.category.dto.CategoryRequestDTO;
import com.example.backend.category.dto.CategoryResponseDTO;
import com.example.backend.category.entity.Category;

import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toEntity(CategoryRequestDTO dto);

    CategoryResponseDTO toDto(Category entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(CategoryRequestDTO dto, @MappingTarget Category entity);
}
