package com.example.backend.user.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.backend.common.exception.ResourceNotFoundException;
import com.example.backend.user.dto.ChangePasswordRequestDTO;
import com.example.backend.user.dto.UserRequestDTO;
import com.example.backend.user.dto.UserResponseDTO;
import com.example.backend.user.entity.User;
import com.example.backend.user.mapper.UserMapper;
import com.example.backend.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));
        return userMapper.toDto(user);
    }

    public UserResponseDTO createUser(UserRequestDTO dto) {

        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new IllegalArgumentException("Username already exists.");
        }
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email already exists.");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());

        // TODO: replace with BCrypt later
        user.setPasswordHash(dto.getPassword());

        user.setIsActive(true);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        return userMapper.toDto(userRepository.save(user));
    }

    public UserResponseDTO updateUser(Long id, UserRequestDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));

        user.setUsername(dto.getUsername());
        user.setPhone(dto.getPhone());
        user.setUpdatedAt(LocalDateTime.now());

        return userMapper.toDto(userRepository.save(user));
    }

    public void deactivateUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));

        user.setIsActive(false);
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    public void activateUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));

        user.setIsActive(true);
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    public void changePassword(Long userId, ChangePasswordRequestDTO dto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));

        // 1️⃣ 檢查新密碼與確認密碼
        if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
            throw new IllegalArgumentException("New password and confirm password do not match.");
        }

        // 2️⃣（暫時版）檢查舊密碼
        // ⚠️ 未使用加密前，只能直接比對
        if (!user.getPasswordHash().equals(dto.getCurrentPassword())) {
            throw new IllegalArgumentException("Current password is incorrect.");
        }

        // 3️⃣ 設定新密碼
        // TODO: replace with BCrypt
        user.setPasswordHash(dto.getNewPassword());

        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }

}
