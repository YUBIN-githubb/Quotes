package com.example.quotes.domain.user.dto.request;

import com.example.quotes.common.consts.Const;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UpdatePasswordRequest {

    @NotBlank
    private String password;

    @NotBlank
    private String newPassword;

}
