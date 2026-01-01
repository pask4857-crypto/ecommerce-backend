package com.example.backend.admin.dto;

import java.time.LocalDateTime;

import com.example.backend.user.entity.UserRole;
import com.example.backend.user.entity.UserStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdminUserResponse {

    private Long id;
    private String email;
    private String name;
    private String phone;
    private UserRole role;
    private UserStatus status;
    private LocalDateTime createdAt;
}
