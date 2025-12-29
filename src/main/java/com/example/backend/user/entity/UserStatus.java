package com.example.backend.user.entity;

public enum UserStatus {
    ACTIVE,
    SUSPENDED,
    DELETED;

    public boolean isActive() {
        return this == ACTIVE;
    }
}
