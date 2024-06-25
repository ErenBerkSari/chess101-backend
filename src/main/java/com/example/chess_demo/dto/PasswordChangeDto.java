package com.example.chess_demo.dto;

import lombok.Data;

@Data
public class PasswordChangeDto {
    private String currentPassword;
    private String newPassword;

    // Getters and setters
}

