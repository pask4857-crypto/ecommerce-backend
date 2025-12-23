package com.example.backend.cart.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public void addToCart(Long userId, AddToCartRequest request) {

        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> cartRepository.save(Cart.create(userId)));

        cartItemRepository
                .findByCartIdAndProductVariantId(cart.getId(), request.getProductVariantId())
                .ifPresentOrElse(
                        item -> item.increaseQuantity(request.getQuantity()),
                        () -> cartItemRepository.save(
                                CartItem.create(
                                        cart.getId(),
                                        request.getProductVariantId(),
                                        request.getQuantity())));

        cart.touch();
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
                .toList();
    }
}
