package com.example.backend.cart.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.backend.cart.dto.AddToCartRequest;
import com.example.backend.cart.dto.CartItemResponse;
import com.example.backend.cart.entity.Cart;
import com.example.backend.cart.entity.CartItem;
import com.example.backend.cart.repository.CartItemRepository;
import com.example.backend.cart.repository.CartRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public void addToCart(Long userId, AddToCartRequest request) {

        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> createCart(userId));

        CartItem cartItem = cartItemRepository
                .findByCartIdAndProductVariantId(cart.getId(), request.getProductVariantId())
                .orElseGet(() -> createCartItem(cart.getId(), request));

        if (cartItem.getId() != null) {
            cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());
            cartItem.setUpdatedAt(LocalDateTime.now());
            cartItemRepository.save(cartItem);
        }
    }

    public List<CartItemResponse> getCartItems(Long userId) {

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        return cartItemRepository.findByCartId(cart.getId())
                .stream()
                .map(item -> new CartItemResponse(
                        item.getId(),
                        item.getProductVariantId(),
                        item.getQuantity()))
                .collect(Collectors.toList());
    }

    /*
     * =========================
     * Private Helpers
     * =========================
     */

    private Cart createCart(Long userId) {
        Cart cart = new Cart();
        cart.setUserId(userId);
        cart.setCreatedAt(LocalDateTime.now());
        cart.setUpdatedAt(LocalDateTime.now());
        return cartRepository.save(cart);
    }

    private CartItem createCartItem(Long cartId, AddToCartRequest request) {
        CartItem item = new CartItem();
        item.setCartId(cartId);
        item.setProductVariantId(request.getProductVariantId());
        item.setQuantity(request.getQuantity());
        item.setCreatedAt(LocalDateTime.now());
        item.setUpdatedAt(LocalDateTime.now());
        return cartItemRepository.save(item);
    }
}
