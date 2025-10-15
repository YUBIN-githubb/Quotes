package com.example.quotes.domain.user.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class WithdrawUserRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
