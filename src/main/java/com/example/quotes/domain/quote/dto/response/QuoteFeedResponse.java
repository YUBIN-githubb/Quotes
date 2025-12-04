package com.example.quotes.domain.quote.dto.response;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;

@Getter
public class QuoteFeedResponse {

    @JsonUnwrapped
    private final QuoteResponse quoteResponse;

    private final Long myLikeId;

    private QuoteFeedResponse(Long likeId, QuoteResponse quoteResponse) {
        this.myLikeId = likeId;
        this.quoteResponse = quoteResponse;
    }

    public static QuoteFeedResponse of(QuoteResponse quoteResponse, Long likeId) {
        return new QuoteFeedResponse(likeId, quoteResponse);
    }
}
