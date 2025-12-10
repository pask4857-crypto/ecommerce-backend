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

import com.example.backend.cart.dto.CartCouponRequestDTO;
import com.example.backend.cart.dto.CartCouponResponseDTO;
import com.example.backend.cart.service.CartCouponService;

@RestController
@RequestMapping("/api/cart-coupons")
public class CartCouponController {

    private final CartCouponService cartCouponService;

    public CartCouponController(CartCouponService cartCouponService) {
        this.cartCouponService = cartCouponService;
    }

    @PostMapping
    public CartCouponResponseDTO create(@RequestBody CartCouponRequestDTO dto) {
        return cartCouponService.create(dto);
    }

    @PutMapping("/{id}")
    public CartCouponResponseDTO update(@PathVariable Long id, @RequestBody CartCouponRequestDTO dto) {
        return cartCouponService.update(id, dto);
    }

    @GetMapping("/{id}")
    public CartCouponResponseDTO getById(@PathVariable Long id) {
        return cartCouponService.getById(id);
    }

    @GetMapping("/cart/{cartId}")
    public List<CartCouponResponseDTO> getByCart(@PathVariable Long cartId) {
        return cartCouponService.getByCart(cartId);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        cartCouponService.delete(id);
    }
}
