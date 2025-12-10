package com.example.backend.cart.service;

import com.example.backend.cart.dto.CartRequestDTO;
import com.example.backend.cart.dto.CartResponseDTO;
import com.example.backend.cart.entity.Cart;
import com.example.backend.cart.mapper.CartMapper;
import com.example.backend.cart.repository.CartRepository;
import com.example.backend.common.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;

    public CartService(CartRepository cartRepository, CartMapper cartMapper) {
        this.cartRepository = cartRepository;
        this.cartMapper = cartMapper;
    }

    public CartResponseDTO create(CartRequestDTO dto) {
        Cart cart = cartMapper.toEntity(dto);
        Cart saved = cartRepository.save(cart);
        return cartMapper.toResponseDTO(saved);
    }

    public CartResponseDTO update(Long id, CartRequestDTO dto) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found with id: " + id));

        cart.setUserId(dto.getUserId());
        cart.setTotalAmount(dto.getTotalAmount());
        cart.setDiscountAmount(dto.getDiscountAmount());
        cart.setFinalAmount(dto.getFinalAmount());

        Cart saved = cartRepository.save(cart);
        return cartMapper.toResponseDTO(saved);
    }

    public CartResponseDTO getById(Long id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found with id: " + id));

        return cartMapper.toResponseDTO(cart);
    }

    public List<CartResponseDTO> getByUser(Long userId) {
        return cartRepository.findByUserId(userId)
                .stream()
                .map(cartMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public void delete(Long id) {
        if (!cartRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cart not found with id: " + id);
        }
        cartRepository.deleteById(id);
    }
}
