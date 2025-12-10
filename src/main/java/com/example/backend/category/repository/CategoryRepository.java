package com.example.backend.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.category.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
