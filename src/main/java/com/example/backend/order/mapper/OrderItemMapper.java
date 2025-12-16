package com.example.backend.order.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.example.backend.order.dto.OrderItemRequestDTO;
import com.example.backend.order.dto.OrderItemResponseDTO;
import com.example.backend.order.entity.OrderItem;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    OrderItem toEntity(OrderItemRequestDTO dto);

    OrderItemResponseDTO toDto(OrderItem entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(OrderItemRequestDTO dto, @MappingTarget OrderItem entity);
}
