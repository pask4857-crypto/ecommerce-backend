package com.example.backend.cart.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.cart.dto.CartResponseDTO;
import com.example.backend.cart.service.CartService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("/user/{userId}")
    public CartResponseDTO getCartByUserId(@PathVariable Long userId) {
        return cartService.getCartByUserId(userId);
    }

    @PostMapping("/user/{userId}")
    public CartResponseDTO createCart(@PathVariable Long userId) {
        return cartService.createCart(userId);
    }

    @DeleteMapping("/user/{userId}/{cartId}")
    public void deleteCart(
            @PathVariable Long userId,
            @PathVariable Long cartId) {
        cartService.deleteCart(userId, cartId);
    }

}
