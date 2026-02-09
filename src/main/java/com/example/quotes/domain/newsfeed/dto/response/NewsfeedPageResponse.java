package com.example.quotes.domain.newsfeed.dto.response;

import com.example.quotes.domain.quote.dto.response.QuoteFeedResponse;
import lombok.Getter;

import java.util.List;

@Getter
public class NewsfeedPageResponse {

    private final List<QuoteFeedResponse> content;
    private final int page;
    private final int size;
    private final long totalElements;
    private final int totalPages;
    private final boolean hasNext;
    private final boolean hasPrevious;

    private NewsfeedPageResponse(List<QuoteFeedResponse> content, int page, int size, long totalElements) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = size > 0 ? (int) Math.ceil((double) totalElements / size) : 0;
        this.hasNext = page < totalPages - 1;
        this.hasPrevious = page > 0;
    }

    public static NewsfeedPageResponse of(List<QuoteFeedResponse> content, int page, int size, long totalElements) {
        return new NewsfeedPageResponse(content, page, size, totalElements);
    }
}
