package com.example.quotes.domain.quote.dto.response;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Getter;

@Getter
public class QuoteProfileResponse {

    @JsonUnwrapped
    private final QuoteResponse quoteResponse;

    private final Long myLikeId;
    private final Long likeCount;

    private QuoteProfileResponse(QuoteResponse quoteResponse, Long myLikeId, Long likeCount) {
        this.quoteResponse = quoteResponse;
        this.myLikeId = myLikeId;
        this.likeCount = likeCount;
    }

    public static QuoteProfileResponse of(QuoteResponse quoteResponse, Long myLikeId, Long likeCount) {
        return new QuoteProfileResponse(quoteResponse, myLikeId, likeCount);
    }
}
