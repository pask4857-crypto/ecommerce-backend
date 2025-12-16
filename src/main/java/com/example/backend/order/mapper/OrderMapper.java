package com.example.backend.order.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.example.backend.order.dto.OrderRequestDTO;
import com.example.backend.order.dto.OrderResponseDTO;
import com.example.backend.order.entity.Order;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    Order toEntity(OrderRequestDTO dto);

    OrderResponseDTO toDto(Order entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(OrderRequestDTO dto, @MappingTarget Order entity);
}
