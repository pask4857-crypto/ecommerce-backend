package com.example.backend.cart.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.backend.cart.dto.AddToCartRequest;
import com.example.backend.cart.dto.CartItemResponse;
import com.example.backend.cart.service.CartService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/{userId}/items")
    public ResponseEntity<Void> addToCart(
            @PathVariable Long userId,
            @RequestBody AddToCartRequest request) {
        cartService.addToCart(userId, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}/items")
    public ResponseEntity<List<CartItemResponse>> getCartItems(
            @PathVariable Long userId) {
        return ResponseEntity.ok(cartService.getCartItems(userId));
    }
}
