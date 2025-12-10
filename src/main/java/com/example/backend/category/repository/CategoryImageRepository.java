package com.example.backend.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.backend.category.entity.CategoryImage;

import java.util.List;

@Repository
public interface CategoryImageRepository extends JpaRepository<CategoryImage, Long> {

    List<CategoryImage> findByCategoryId(Long categoryId);
}
