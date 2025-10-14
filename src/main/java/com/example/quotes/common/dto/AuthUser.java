package com.example.quotes.common.dto;

import com.example.quotes.common.enums.UserRole;
import lombok.Getter;

@Getter
public class AuthUser {
    private final String email;
    private final UserRole userRole;

    public AuthUser(String email, UserRole userRole) {
        this.email = email;
        this.userRole = userRole;
    }
}
