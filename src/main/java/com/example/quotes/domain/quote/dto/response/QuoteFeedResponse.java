package com.example.quotes.domain.quote.dto.response;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;

@Getter
public class QuoteFeedResponse {

    @JsonUnwrapped
    private final QuoteResponse quoteResponse;

    private final Long myLikeId;
    private final Long likeCount;

    private QuoteFeedResponse(Long likeId, Long likeCount, QuoteResponse quoteResponse) {
        this.myLikeId = likeId;
        this.likeCount = likeCount;
        this.quoteResponse = quoteResponse;
    }

    public static QuoteFeedResponse of(QuoteResponse quoteResponse, Long likeId, Long likeCount) {
        return new QuoteFeedResponse(likeId, likeCount, quoteResponse);
    }
}
