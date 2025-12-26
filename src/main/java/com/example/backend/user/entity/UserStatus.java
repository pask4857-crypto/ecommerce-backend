package com.example.backend.user.entity;

public enum UserStatus {
    ACTIVE,
    SUSPENDED,
    DELETED;

    public boolean isLoginAllowed() {
        return this == ACTIVE;
    }
}
