package com.example.quotes.domain.auth.dto.request;

import com.example.quotes.common.consts.Const;
import com.example.quotes.common.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class SignUpUserRequest {

    @Email(message = "이메일 형식이 올바르지 않습니다.")
    @NotBlank(message = "이메일 입력은 필수입니다.")
    private String email;

    @Pattern(
            regexp = Const.PASSWORD_PATTERN,
            message = "비밀번호 형식이 올바르지 않습니다."
    )
    @NotBlank(message = "비밀번호 입력은 필수입니다.")
    private String password;

    @NotNull(message = "역할 입력은 필수입니다.")
    private UserRole userRole;

    @NotBlank(message = "프로필 사진 입력은 필수입니다.")
    private String profileUrl;

    @NotBlank(message = "닉네임 입력은 필수입니다.")
    private String nickname;
}
