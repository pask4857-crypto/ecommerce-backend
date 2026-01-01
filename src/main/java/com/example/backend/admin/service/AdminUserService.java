package com.example.backend.admin.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.backend.admin.dto.AdminUserResponse;
import com.example.backend.user.entity.User;
import com.example.backend.user.entity.UserStatus;
import com.example.backend.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminUserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<AdminUserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private AdminUserResponse toResponse(User user) {
        return new AdminUserResponse(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getPhone(),
                user.getRole(),
                user.getStatus(),
                user.getCreatedAt());
    }

    @Transactional
    public void suspendUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("使用者不存在"));

        if (user.getStatus() == UserStatus.SUSPENDED) {
            throw new IllegalStateException("使用者已被停用");
        }

        user.deactivate();
    }

    @Transactional
    public void reactivateUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("使用者不存在"));

        user.reactivate();
    }

}
