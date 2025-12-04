package com.example.quotes.domain.like.dto.response;

import lombok.Getter;

@Getter
public class CreateLikeResponse {

    private final Long id;
    private final Long userId;
    private final Long quoteId;

    private CreateLikeResponse(Long id, Long userId, Long quoteId) {
        this.id = id;
        this.userId = userId;
        this.quoteId = quoteId;
    }

    public static CreateLikeResponse of(Long id, Long userId, Long quoteId) {
        return new CreateLikeResponse(id, userId, quoteId);
    }
}
