package com.example.quotes.domain.quote.dto.request;

import com.example.quotes.common.enums.Category;
import com.example.quotes.common.enums.IsPublic;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateQuoteRequest {

    @NotBlank(message = "제목 입력은 필수입니다.")
    private String title;

    @NotBlank(message = "저자 입력은 필수입니다.")
    private String author;

    @NotNull(message = "카테고리 입력은 필수입니다.")
    private Category category;

    @NotNull(message = "페이지 번호 입력은 필수입니다.")
    private Long pageNumber;

    @NotBlank(message = "필사 문장 입력은 필수입니다.")
    private String sentence;

    @NotBlank(message = "생각 입력은 필수입니다.")
    private String thought;

    @NotNull(message = "공개 여부 입력은 필수입니다.")
    private IsPublic isPublic;
}
