package com.example.backend.admin.service;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.backend.admin.dto.AdminUserResponse;
import com.example.backend.user.entity.User;
import com.example.backend.user.entity.UserRole;
import com.example.backend.user.entity.UserStatus;
import com.example.backend.user.repository.UserRepository;
import com.example.backend.user.security.CustomUserDetails;

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

    private CustomUserDetails currentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !(auth.getPrincipal() instanceof CustomUserDetails user)) {
            throw new IllegalStateException("未登入或登入資訊異常");
        }

        return user;
    }

    @Transactional
    public void suspendUser(Long targetUserId) {

        CustomUserDetails current = currentUser();

        if (current.getUserId().equals(targetUserId)) {
            throw new IllegalStateException("不可停用自己的帳號");
        }

        User target = userRepository.findById(targetUserId)
                .orElseThrow(() -> new IllegalArgumentException("使用者不存在"));

        if (target.getRole() == UserRole.ADMIN && target.getStatus() == UserStatus.ACTIVE) {

            long activeAdminCount = userRepository.countByRoleAndStatus(UserRole.ADMIN, UserStatus.ACTIVE);

            if (activeAdminCount <= 1) {
                throw new IllegalStateException("不可停用最後一個管理員帳號");
            }
        }

        target.deactivate();
    }

    @Transactional
    public void reactivateUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("使用者不存在"));

        user.reactivate();
    }

}
