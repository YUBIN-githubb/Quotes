package com.example.quotes.domain.like.dto.response;

import com.example.quotes.common.enums.Category;
import com.example.quotes.common.enums.IsPublic;
import com.example.quotes.domain.like.entity.Like;
import com.example.quotes.domain.quote.dto.response.PageQuoteResponse;
import com.example.quotes.domain.quote.dto.response.QuoteResponse;
import com.example.quotes.domain.quote.entity.Quote;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PageLikeResponse {

    private final List<LikeResponse> content;
    private final int size;
    private final int page;
    private final long totalElements;
    private final int totalPages;

    private PageLikeResponse(List<LikeResponse> content, int size, int page, long totalElements, int totalPages) {
        this.content = content;
        this.size = size;
        this.page = page;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public static PageLikeResponse of(List<Like> content, int size, int page, long totalElements, int totalPages) {
        List<LikeResponse> modifiedContent = content.stream().map(
                like -> {
                    Long quoteId = like.getQuote().getId();
                    String title = like.getQuote().getTitle();
                    Category category = like.getQuote().getCategory();
                    return LikeResponse.of(quoteId, title, category);
                }
        ).toList();
        return new PageLikeResponse(modifiedContent, size, page, totalElements, totalPages);
    }
}
