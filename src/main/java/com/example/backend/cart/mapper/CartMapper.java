package com.example.backend.cart.mapper;

import com.example.backend.cart.dto.CartRequestDTO;
import com.example.backend.cart.dto.CartResponseDTO;
import com.example.backend.cart.entity.Cart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMapper {

    Cart toEntity(CartRequestDTO dto);

    CartResponseDTO toResponseDTO(Cart entity);
}
