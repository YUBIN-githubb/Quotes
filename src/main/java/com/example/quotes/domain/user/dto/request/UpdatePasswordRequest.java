package com.example.quotes.domain.user.dto.request;

import com.example.quotes.common.consts.Const;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UpdatePasswordRequest {

    @Pattern(
            regexp = Const.PASSWORD_PATTERN,
            message = "비밀번호 형식이 올바르지 않습니다."
    )
    @NotBlank(message = "비밀번호 입력은 필수입니다.")
    private String oldPassword;

    @Pattern(
            regexp = Const.PASSWORD_PATTERN,
            message = "비밀번호 형식이 올바르지 않습니다."
    )
    @NotBlank(message = "비밀번호 입력은 필수입니다.")
    private String newPassword;

}
