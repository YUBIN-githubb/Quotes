package com.example.quotes.domain.like.dto.response;

import com.example.quotes.common.enums.Category;
import lombok.Getter;

@Getter
public class LikeResponse {

    private final Long quoteId;
    private final String title;
    private final Category category;

    private LikeResponse(Long quoteId, String title, Category category) {
        this.quoteId = quoteId;
        this.title = title;
        this.category = category;
    }

    public static LikeResponse of(Long quoteId, String title, Category category) {
        return new LikeResponse(quoteId, title, category);
    }
}
