package com.example.backend.user.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.user.dto.AddressRequestDTO;
import com.example.backend.user.dto.AddressResponseDTO;
import com.example.backend.user.service.AddressService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @GetMapping("/user/{userId}")
    public List<AddressResponseDTO> getByUser(@PathVariable Long userId) {
        return addressService.getAddressesByUser(userId);
    }

    @GetMapping("/{id}")
    public AddressResponseDTO getById(@PathVariable Long id) {
        return addressService.getAddressById(id);
    }

    @PostMapping
    public AddressResponseDTO create(@RequestBody AddressRequestDTO dto) {
        return addressService.createAddress(dto);
    }

    @PutMapping("/{id}")
    public AddressResponseDTO update(@PathVariable Long id, @RequestBody AddressRequestDTO dto) {
        return addressService.updateAddress(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        addressService.deleteAddress(id);
    }
}
