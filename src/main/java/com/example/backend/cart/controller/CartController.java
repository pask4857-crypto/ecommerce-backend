package com.example.backend.cart.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.cart.dto.CartRequestDTO;
import com.example.backend.cart.dto.CartResponseDTO;
import com.example.backend.cart.service.CartService;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public CartResponseDTO create(@RequestBody CartRequestDTO dto) {
        return cartService.create(dto);
    }

    @PutMapping("/{id}")
    public CartResponseDTO update(@PathVariable Long id, @RequestBody CartRequestDTO dto) {
        return cartService.update(id, dto);
    }

    @GetMapping("/{id}")
    public CartResponseDTO getById(@PathVariable Long id) {
        return cartService.getById(id);
    }

    @GetMapping("/user/{userId}")
    public List<CartResponseDTO> getByUser(@PathVariable Long userId) {
        return cartService.getByUser(userId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        cartService.delete(id);
    }
}
