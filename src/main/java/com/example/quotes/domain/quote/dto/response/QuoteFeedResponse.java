package com.example.quotes.domain.quote.dto.response;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;

@Getter
public class QuoteFeedResponse {

    @JsonUnwrapped
    private final QuoteResponse quoteResponse;

    private final boolean isLiked;
    private final Long likeCount;

    private QuoteFeedResponse(boolean isLiked, Long likeCount, QuoteResponse quoteResponse) {
        this.isLiked = isLiked;
        this.likeCount = likeCount;
        this.quoteResponse = quoteResponse;
    }

    public static QuoteFeedResponse of(QuoteResponse quoteResponse, boolean isLiked, Long likeCount) {
        return new QuoteFeedResponse(isLiked, likeCount, quoteResponse);
    }
}
