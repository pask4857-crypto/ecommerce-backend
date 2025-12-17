package com.example.backend.cart.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.cart.dto.CartItemRequestDTO;
import com.example.backend.cart.dto.CartItemResponseDTO;
import com.example.backend.cart.service.CartItemService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cart-items")
@RequiredArgsConstructor
public class CartItemController {

    private final CartItemService cartItemService;

    @GetMapping("/cart/{cartId}")
    public List<CartItemResponseDTO> getItemsByCartId(@PathVariable Long cartId) {
        return cartItemService.getItemsByCartId(cartId);
    }

    @PostMapping
    public CartItemResponseDTO addCartItem(@RequestBody CartItemRequestDTO dto) {
        return cartItemService.addCartItem(dto.getCartId(), dto.getProductId(), dto.getQuantity(), dto.getUnitPrice());
    }

    @DeleteMapping("/{cartItemId}")
    public void deleteCartItem(@PathVariable Long cartItemId) {
        cartItemService.deleteCartItem(cartItemId);
    }

    @DeleteMapping("/cart/{cartId}")
    public void clearCart(@PathVariable Long cartId) {
        cartItemService.clearCart(cartId);
    }
}
