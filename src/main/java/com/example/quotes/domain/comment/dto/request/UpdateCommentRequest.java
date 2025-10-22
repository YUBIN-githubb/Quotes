package com.example.quotes.domain.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UpdateCommentRequest {

    @NotBlank(message = "댓글 내용 입력은 필수입니다.")
    private String contents;
}
