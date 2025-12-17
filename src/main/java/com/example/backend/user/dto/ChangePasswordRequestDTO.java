package com.example.backend.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequestDTO {

    // 目前密碼（未來可選）
    private String currentPassword;

    // 新密碼
    private String newPassword;

    // 新密碼確認
    private String confirmPassword;
}
