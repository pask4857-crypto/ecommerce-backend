package com.example.backend.cart.service;

import org.springframework.stereotype.Service;

import com.example.backend.cart.dto.CartResponseDTO;
import com.example.backend.cart.entity.Cart;
import com.example.backend.cart.repository.CartRepository;
import com.example.backend.common.exception.ResourceNotFoundException;
import com.example.backend.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    private final UserService userService;

    public CartResponseDTO getCartByUserId(Long userId) {

        userService.validateUserIsActive(userId);

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user: " + userId));
        return new CartResponseDTO(cart.getCartId(), cart.getUserId());
    }

    public CartResponseDTO createCart(Long userId) {
        Cart cart = Cart.create(userId);
        return new CartResponseDTO(cartRepository.save(cart).getCartId(), userId);
    }

    public void deleteCart(Long cartId) {
        if (!cartRepository.existsById(cartId)) {
            throw new ResourceNotFoundException("Cart not found: " + cartId);
        }
        cartRepository.deleteById(cartId);
    }
}
