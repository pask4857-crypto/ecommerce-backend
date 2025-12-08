package com.example.backend.repository;

import com.example.backend.entity.CategoryImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryImageRepository extends JpaRepository<CategoryImage, Long> {

    List<CategoryImage> findByCategoryId(Long categoryId);
}
