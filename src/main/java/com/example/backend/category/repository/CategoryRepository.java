package com.example.backend.category.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.category.entity.Category;
import com.example.backend.category.entity.CategoryStatus;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByStatus(CategoryStatus status);

    boolean existsByName(String name);

    boolean existsBySlug(String slug);
}
