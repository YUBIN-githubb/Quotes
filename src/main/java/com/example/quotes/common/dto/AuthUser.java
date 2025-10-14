package com.example.quotes.common.dto;

import com.example.quotes.common.enums.UserRole;
import lombok.Getter;

@Getter
public class AuthUser {
    private final Long userId;
    private final String email;
    private final UserRole userRole;

    public AuthUser(Long userId, String email, UserRole userRole) {
        this.userId = userId;
        this.email = email;
        this.userRole = userRole;
    }
}
