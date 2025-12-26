package com.example.backend.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.backend.user.dto.LoginRequest;
import com.example.backend.user.dto.LoginResponse;
import com.example.backend.user.dto.RegisterRequest;
import com.example.backend.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Long> register(@RequestBody RegisterRequest request) {
        Long userId = userService.register(request);
        return ResponseEntity.ok(userId);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }
}
