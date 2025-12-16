package com.example.backend.user.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.example.backend.user.dto.AddressRequestDTO;
import com.example.backend.user.dto.AddressResponseDTO;
import com.example.backend.user.entity.Address;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    Address toEntity(AddressRequestDTO dto);

    AddressResponseDTO toDto(Address entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(AddressRequestDTO dto, @MappingTarget Address entity);
}
