package com.example.backend.cart.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.example.backend.cart.dto.CartItemRequestDTO;
import com.example.backend.cart.dto.CartItemResponseDTO;
import com.example.backend.cart.entity.CartItem;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

    CartItem toEntity(CartItemRequestDTO dto);

    CartItemResponseDTO toDto(CartItem entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(CartItemRequestDTO dto, @MappingTarget CartItem entity);
}
