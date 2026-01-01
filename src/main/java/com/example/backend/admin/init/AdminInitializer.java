package com.example.backend.admin.init;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.backend.user.entity.User;
import com.example.backend.user.entity.UserRole;
import com.example.backend.user.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class AdminInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void initAdmin() {

        // 如果系統中已經有 ADMIN，就不再建立
        if (userRepository.existsByRole(UserRole.ADMIN)) {
            return;
        }

        User admin = User.createAdmin(
                "admin@example.com",
                passwordEncoder.encode("admin123"));

        userRepository.save(admin);

        System.out.println("預設 ADMIN 帳號已建立：admin@example.com / admin123");
    }
}
