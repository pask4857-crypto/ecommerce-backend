package com.example.backend.cart.repository;

import com.example.backend.cart.entity.CartCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartCouponRepository extends JpaRepository<CartCoupon, Long> {
    List<CartCoupon> findByCartId(Long cartId);
}
