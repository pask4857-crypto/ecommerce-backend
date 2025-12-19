package com.example.backend.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.backend.common.exception.ResourceNotFoundException;
import com.example.backend.user.dto.LoginRequestDTO;
import com.example.backend.user.dto.RegisterRequestDTO;
import com.example.backend.user.dto.UserResponseDTO;
import com.example.backend.user.entity.User;
import com.example.backend.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /*
     * ======================
     * 註冊
     * ======================
     */
    @Transactional
    public UserResponseDTO register(RegisterRequestDTO request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }

        String passwordHash = passwordEncoder.encode(request.getPassword());

        User user = User.register(request.getEmail(), passwordHash);
        userRepository.save(user);

        return new UserResponseDTO(
                user.getUserId(),
                user.getEmail(),
                user.isActive());
    }

    /*
     * ======================
     * 登入
     * ======================
     */
    public UserResponseDTO login(LoginRequestDTO request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        if (!user.isActive()) {
            throw new IllegalStateException("User is deactivated");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        return new UserResponseDTO(
                user.getUserId(),
                user.getEmail(),
                user.isActive());
    }

    /*
     * ======================
     * 共用驗證（你已在用）
     * ======================
     */
    public void validateUserIsActive(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!user.isActive()) {
            throw new IllegalStateException("User is deactivated");
        }
    }
}
