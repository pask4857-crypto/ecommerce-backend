package com.example.backend.category.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categories")
@Getter
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoryStatus status;

    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private Category parentCategory;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    /*
     * =========================
     * Factory Method
     * =========================
     */
    public static Category create(String name, String slug, String description) {
        Category category = new Category();
        category.name = name;
        category.slug = slug;
        category.description = description;
        category.status = CategoryStatus.ACTIVE;
        category.createdAt = LocalDateTime.now();
        category.updatedAt = LocalDateTime.now();
        return category;
    }

    /*
     * =========================
     * Domain Methods
     * =========================
     */

    public void rename(String newName, String newSlug) {
        this.name = newName;
        this.slug = newSlug;
        this.updatedAt = LocalDateTime.now();
    }

    public void update(String name, String description) {
        this.name = name;
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }

    public void activate() {
        if (this.status == CategoryStatus.ACTIVE) {
            return;
        }
        this.status = CategoryStatus.ACTIVE;
        this.updatedAt = LocalDateTime.now();
    }

    public void deactivate() {
        if (this.status == CategoryStatus.INACTIVE) {
            return;
        }
        this.status = CategoryStatus.INACTIVE;
        this.updatedAt = LocalDateTime.now();
    }
}
