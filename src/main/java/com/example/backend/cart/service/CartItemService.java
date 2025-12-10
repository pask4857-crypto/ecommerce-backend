package com.example.backend.cart.service;

import com.example.backend.cart.dto.CartItemRequestDTO;
import com.example.backend.cart.dto.CartItemResponseDTO;
import com.example.backend.cart.entity.CartItem;
import com.example.backend.cart.mapper.CartItemMapper;
import com.example.backend.cart.repository.CartItemRepository;
import com.example.backend.common.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final CartItemMapper cartItemMapper;

    public CartItemService(
            CartItemRepository cartItemRepository,
            CartItemMapper cartItemMapper) {
        this.cartItemRepository = cartItemRepository;
        this.cartItemMapper = cartItemMapper;
    }

    public CartItemResponseDTO create(CartItemRequestDTO dto) {
        CartItem entity = cartItemMapper.toEntity(dto);
        CartItem saved = cartItemRepository.save(entity);
        return cartItemMapper.toResponseDTO(saved);
    }

    public CartItemResponseDTO update(Long id, CartItemRequestDTO dto) {
        CartItem entity = cartItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CartItem not found with id: " + id));

        entity.setQuantity(dto.getQuantity());
        entity.setUnitPrice(dto.getUnitPrice());
        entity.setTotalPrice(dto.getUnitPrice() * dto.getQuantity());

        CartItem saved = cartItemRepository.save(entity);
        return cartItemMapper.toResponseDTO(saved);
    }

    public CartItemResponseDTO getById(Long id) {
        CartItem entity = cartItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CartItem not found with id: " + id));
        return cartItemMapper.toResponseDTO(entity);
    }

    public List<CartItemResponseDTO> getByCart(Long cartId) {
        return cartItemRepository.findByCartId(cartId)
                .stream()
                .map(cartItemMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public void delete(Long id) {
        if (!cartItemRepository.existsById(id)) {
            throw new ResourceNotFoundException("CartItem not found with id: " + id);
        }
        cartItemRepository.deleteById(id);
    }
}
