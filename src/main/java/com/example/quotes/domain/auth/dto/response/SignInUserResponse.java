package com.example.quotes.domain.auth.dto.response;

import com.example.quotes.common.enums.UserRole;
import lombok.Getter;

@Getter
public class SignInUserResponse {

    private final String token;

    private SignInUserResponse(String token) {
        this.token = token;
    }

    public static SignInUserResponse of(String token) {
        return new SignInUserResponse(token);
    }
}
