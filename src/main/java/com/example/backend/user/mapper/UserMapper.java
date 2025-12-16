package com.example.backend.user.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.example.backend.user.dto.UserRequestDTO;
import com.example.backend.user.dto.UserResponseDTO;
import com.example.backend.user.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "passwordHash", source = "password")
    User toEntity(UserRequestDTO dto);

    UserResponseDTO toDto(User entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "passwordHash", source = "password")
    void updateEntityFromDto(UserRequestDTO dto, @MappingTarget User entity);
}
