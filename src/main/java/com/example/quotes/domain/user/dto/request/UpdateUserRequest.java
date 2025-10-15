package com.example.quotes.domain.user.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UpdateUserRequest {

    @NotBlank
    private String profileUrl;

    @NotBlank
    private String nickname;

}
