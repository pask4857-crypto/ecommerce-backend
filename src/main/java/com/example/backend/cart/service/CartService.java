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

        userService.validateUserIsActive(userId);

        if (cartRepository.findByUserId(userId).isPresent()) {
            throw new IllegalStateException("Cart already exists for user: " + userId);
        }

        Cart cart = Cart.create(userId);
        Cart saved = cartRepository.save(cart);

        return new CartResponseDTO(saved.getCartId(), saved.getUserId());

    }

    public void deleteCart(Long userId, Long cartId) {

        userService.validateUserIsActive(userId);

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found: " + cartId));

        if (!cart.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Cart does not belong to user: " + userId);
        }

        cartRepository.deleteById(cartId);
    }
}
