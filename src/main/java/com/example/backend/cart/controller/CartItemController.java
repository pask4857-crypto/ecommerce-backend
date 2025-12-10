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

import com.example.backend.cart.dto.CartItemRequestDTO;
import com.example.backend.cart.dto.CartItemResponseDTO;
import com.example.backend.cart.service.CartItemService;

@RestController
@RequestMapping("/api/cart-items")
public class CartItemController {

    private final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PostMapping
    public CartItemResponseDTO create(@RequestBody CartItemRequestDTO dto) {
        return cartItemService.create(dto);
    }

    @PutMapping("/{id}")
    public CartItemResponseDTO update(@PathVariable Long id, @RequestBody CartItemRequestDTO dto) {
        return cartItemService.update(id, dto);
    }

    @GetMapping("/{id}")
    public CartItemResponseDTO getById(@PathVariable Long id) {
        return cartItemService.getById(id);
    }

    @GetMapping("/cart/{cartId}")
    public List<CartItemResponseDTO> getByCart(@PathVariable Long cartId) {
        return cartItemService.getByCart(cartId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        cartItemService.delete(id);
    }
}
