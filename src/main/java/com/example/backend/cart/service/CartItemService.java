package com.example.backend.cart.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.backend.cart.dto.CartItemResponseDTO;
import com.example.backend.cart.entity.CartItem;
import com.example.backend.cart.repository.CartItemRepository;
import com.example.backend.cart.repository.CartRepository;
import com.example.backend.common.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;

    public List<CartItemResponseDTO> getItemsByCartId(Long cartId) {
        return cartItemRepository.findByCartId(cartId).stream()
                .map(item -> new CartItemResponseDTO(
                        item.getCartItemId(),
                        item.getCartId(),
                        item.getProductId(),
                        item.getQuantity(),
                        item.getUnitPrice(),
                        item.getTotalPrice()))
                .toList();
    }

    public CartItemResponseDTO addCartItem(
            Long cartId,
            Long productId,
            Integer quantity,
            BigDecimal unitPrice) {

        if (!cartRepository.existsById(cartId)) {
            throw new ResourceNotFoundException("Cart not found: " + cartId);
        }

        CartItem item = CartItem.create(cartId, productId, quantity, unitPrice);
        CartItem saved = cartItemRepository.save(item);

        return new CartItemResponseDTO(
                saved.getCartItemId(),
                saved.getCartId(),
                saved.getProductId(),
                saved.getQuantity(),
                saved.getUnitPrice(),
                saved.getTotalPrice());
    }

    public void deleteCartItem(Long cartItemId) {
        if (!cartItemRepository.existsById(cartItemId)) {
            throw new ResourceNotFoundException("CartItem not found: " + cartItemId);
        }
        cartItemRepository.deleteById(cartItemId);
    }

    public void clearCart(Long cartId) {
        cartItemRepository.deleteAll(
                cartItemRepository.findByCartId(cartId));
    }
}
