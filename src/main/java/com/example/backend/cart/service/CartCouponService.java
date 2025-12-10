package com.example.backend.cart.service;

import com.example.backend.cart.dto.CartCouponRequestDTO;
import com.example.backend.cart.dto.CartCouponResponseDTO;
import com.example.backend.cart.entity.CartCoupon;
import com.example.backend.cart.mapper.CartCouponMapper;
import com.example.backend.cart.repository.CartCouponRepository;
import com.example.backend.common.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartCouponService {

    private final CartCouponRepository cartCouponRepository;
    private final CartCouponMapper cartCouponMapper;

    public CartCouponService(
            CartCouponRepository cartCouponRepository,
            CartCouponMapper cartCouponMapper) {
        this.cartCouponRepository = cartCouponRepository;
        this.cartCouponMapper = cartCouponMapper;
    }

    public CartCouponResponseDTO create(CartCouponRequestDTO dto) {
        CartCoupon entity = cartCouponMapper.toEntity(dto);
        CartCoupon saved = cartCouponRepository.save(entity);
        return cartCouponMapper.toResponseDTO(saved);
    }

    public CartCouponResponseDTO update(Long id, CartCouponRequestDTO dto) {
        CartCoupon entity = cartCouponRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CartCoupon not found with id: " + id));

        entity.setCouponCode(dto.getCouponCode());
        entity.setDiscountAmount(dto.getDiscountAmount());
        entity.setMinSpend(dto.getMinSpend());

        CartCoupon saved = cartCouponRepository.save(entity);
        return cartCouponMapper.toResponseDTO(saved);
    }

    public CartCouponResponseDTO getById(Long id) {
        CartCoupon entity = cartCouponRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CartCoupon not found with id: " + id));

        return cartCouponMapper.toResponseDTO(entity);
    }

    public List<CartCouponResponseDTO> getByCart(Long cartId) {
        return cartCouponRepository.findByCartId(cartId)
                .stream()
                .map(cartCouponMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public void delete(Long id) {
        if (!cartCouponRepository.existsById(id)) {
            throw new ResourceNotFoundException("CartCoupon not found with id: " + id);
        }
        cartCouponRepository.deleteById(id);
    }
}
