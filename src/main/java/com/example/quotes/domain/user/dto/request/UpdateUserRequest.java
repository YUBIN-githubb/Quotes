package com.example.quotes.domain.user.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UpdateUserRequest {

    @NotBlank(message = "프로필 사진 입력은 필수입니다.")
    private String profileUrl;

    @NotBlank(message = "닉네임 입력은 필수입니다.")
    private String nickname;

}
