package com.example.quotes.domain.quotes.dto.request;

import com.example.quotes.common.enums.IsPublic;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateQuoteIsPublicRequest {

    @NotNull(message = "공개 여부 입력은 필수입니다.")
    private IsPublic isPublic;
}
