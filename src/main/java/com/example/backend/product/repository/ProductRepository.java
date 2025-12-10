package com.example.backend.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
