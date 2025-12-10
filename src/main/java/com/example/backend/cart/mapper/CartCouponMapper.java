package com.example.backend.cart.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.backend.cart.dto.CartCouponRequestDTO;
import com.example.backend.cart.dto.CartCouponResponseDTO;
import com.example.backend.cart.entity.CartCoupon;

@Mapper(componentModel = "spring")
public interface CartCouponMapper {

    @Mapping(target = "isActive", expression = "java(true)")
    @Mapping(target = "appliedAt", expression = "java(LocalDateTime.now())")
    CartCoupon toEntity(CartCouponRequestDTO dto);

    CartCouponResponseDTO toResponseDTO(CartCoupon entity);
}
