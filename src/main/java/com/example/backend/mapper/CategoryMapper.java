package com.example.backend.mapper;

import com.example.backend.dto.CategoryRequestDTO;
import com.example.backend.dto.CategoryResponseDTO;
import com.example.backend.entity.Category;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toEntity(CategoryRequestDTO dto);

    CategoryResponseDTO toDto(Category entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(CategoryRequestDTO dto, @MappingTarget Category entity);
}
