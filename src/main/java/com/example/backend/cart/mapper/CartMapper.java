package com.example.backend.cart.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.example.backend.cart.dto.CartRequestDTO;
import com.example.backend.cart.dto.CartResponseDTO;
import com.example.backend.cart.entity.Cart;

@Mapper(componentModel = "spring")
public interface CartMapper {

    Cart toEntity(CartRequestDTO dto);

    CartResponseDTO toDto(Cart entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(CartRequestDTO dto, @MappingTarget Cart entity);
}
