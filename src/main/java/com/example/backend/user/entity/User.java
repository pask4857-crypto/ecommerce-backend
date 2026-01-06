package com.example.backend.user.entity;

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
@Table(name = "users")
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    private String name;

    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public String getPassword() {
        return this.passwordHash;
    }

    /*
     * =========================
     * Factory Method
     * =========================
     */

    public static User createAdmin(String email, String passwordHash) {
        User user = new User();
        user.email = email;
        user.passwordHash = passwordHash;
        user.role = UserRole.ADMIN;
        user.status = UserStatus.ACTIVE;
        user.createdAt = LocalDateTime.now();
        user.updatedAt = LocalDateTime.now();
        return user;
    }

    public static User create(
            String email,
            String passwordHash,
            String name,
            String phone) {
        User user = new User();
        user.email = email;
        user.passwordHash = passwordHash;
        user.name = name;
        user.phone = phone;
        user.role = UserRole.USER;
        user.status = UserStatus.ACTIVE;
        user.createdAt = LocalDateTime.now();
        user.updatedAt = LocalDateTime.now();
        return user;
    }

    /*
     * =========================
     * Domain Method
     * =========================
     */

    public void updateProfile(String name, String phone) {
        this.name = name;
        this.phone = phone;
        this.updatedAt = LocalDateTime.now();
    }

    public void deactivate() {

        if (this.status == UserStatus.SUSPENDED) {
            throw new IllegalStateException("使用者已被停用");
        }

        this.status = UserStatus.SUSPENDED;
        this.updatedAt = LocalDateTime.now();
    }

    public void reactivate() {
        if (this.status != UserStatus.SUSPENDED) {
            throw new IllegalStateException("只有被停用的使用者才能解除停用");
        }
        this.status = UserStatus.ACTIVE;
        this.updatedAt = LocalDateTime.now();
    }

}
