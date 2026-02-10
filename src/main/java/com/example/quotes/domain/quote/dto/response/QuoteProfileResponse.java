package com.example.quotes.domain.quote.dto.response;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;

@Getter
public class QuoteProfileResponse {

    @JsonUnwrapped
    private final QuoteResponse quoteResponse;

    private final boolean isLiked;
    private final Long likeCount;

    private QuoteProfileResponse(QuoteResponse quoteResponse, boolean isLiked, Long likeCount) {
        this.quoteResponse = quoteResponse;
        this.isLiked = isLiked;
        this.likeCount = likeCount;
    }

    public static QuoteProfileResponse of(QuoteResponse quoteResponse, boolean isLiked, Long likeCount) {
        return new QuoteProfileResponse(quoteResponse, isLiked, likeCount);
    }
}
