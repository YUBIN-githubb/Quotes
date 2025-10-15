package com.example.quotes.domain.auth.dto.response;

import com.example.quotes.common.enums.UserRole;
import lombok.Getter;

@Getter
public class SignUpUserResponse {

    private final String email;
    private final UserRole userRole;
    private final String profileUrl;
    private final String nickname;

    private SignUpUserResponse(String email, UserRole userRole, String profileUrl, String nickname) {
        this.email = email;
        this.userRole = userRole;
        this.profileUrl = profileUrl;
        this.nickname = nickname;
    }

    public static SignUpUserResponse of(String email, UserRole userRole, String profileUrl, String nickname) {
        return new SignUpUserResponse(email, userRole, profileUrl, nickname);
    }
}
