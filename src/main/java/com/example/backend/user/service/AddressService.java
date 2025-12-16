package com.example.backend.user.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.backend.common.exception.ResourceNotFoundException;
import com.example.backend.user.dto.AddressRequestDTO;
import com.example.backend.user.dto.AddressResponseDTO;
import com.example.backend.user.entity.Address;
import com.example.backend.user.mapper.AddressMapper;
import com.example.backend.user.repository.AddressRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    public List<AddressResponseDTO> getAddressesByUser(Long userId) {
        return addressRepository.findByUserId(userId)
                .stream()
                .map(addressMapper::toDto)
                .toList();
    }

    public AddressResponseDTO getAddressById(Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found: " + id));
        return addressMapper.toDto(address);
    }

    public AddressResponseDTO createAddress(AddressRequestDTO dto) {
        Address address = addressMapper.toEntity(dto);

        address.setCreatedAt(LocalDateTime.now());
        address.setUpdatedAt(LocalDateTime.now());

        return addressMapper.toDto(addressRepository.save(address));
    }

    public AddressResponseDTO updateAddress(Long id, AddressRequestDTO dto) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found: " + id));

        addressMapper.updateEntityFromDto(dto, address);
        address.setUpdatedAt(LocalDateTime.now());

        return addressMapper.toDto(addressRepository.save(address));
    }

    public void deleteAddress(Long id) {
        if (!addressRepository.existsById(id)) {
            throw new ResourceNotFoundException("Address not found: " + id);
        }
        addressRepository.deleteById(id);
    }
}
