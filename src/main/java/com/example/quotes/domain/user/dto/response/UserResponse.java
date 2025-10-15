package com.example.quotes.domain.user.dto.response;

import com.example.quotes.common.enums.UserRole;
import com.example.quotes.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;

@Getter
public class UserResponse {

    private final String email;
    private final UserRole userRole;
    private final String profileUrl;
    private final String nickname;

    private UserResponse(String email, UserRole userRole, String profileUrl, String nickname) {
        this.email = email;
        this.userRole = userRole;
        this.profileUrl = profileUrl;
        this.nickname = nickname;
    }

    public static UserResponse of(String email, UserRole userRole, String profileUrl, String nickname) {
        return new UserResponse(email, userRole, profileUrl, nickname);
    }
}
