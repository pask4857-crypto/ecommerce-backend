package com.example.backend.user.dto;

import lombok.Getter;

@Getter
public class RegisterRequest {
    private String email;
    private String password;
    private String name;
    private String phone;
}
