package com.example.backend.cart.mapper;

import com.example.backend.cart.dto.CartItemRequestDTO;
import com.example.backend.cart.dto.CartItemResponseDTO;
import com.example.backend.cart.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

    @Mapping(target = "totalPrice", expression = "java(dto.getUnitPrice() * dto.getQuantity())")
    CartItem toEntity(CartItemRequestDTO dto);

    CartItemResponseDTO toResponseDTO(CartItem entity);
}
