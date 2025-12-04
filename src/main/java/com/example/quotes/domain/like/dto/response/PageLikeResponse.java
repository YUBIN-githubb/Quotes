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
                    Long id = like.getId();
                    Long quoteId = like.getQuote().getId();
                    Long userId = like.getUser().getId();
                    String nickname = like.getUser().getNickname();
                    String title = like.getQuote().getTitle();
                    String author = like.getQuote().getAuthor();
                    Category category = like.getQuote().getCategory();
                    Long pageNumber = like.getQuote().getPageNumber();
                    String sentence = like.getQuote().getSentence();
                    String thought = like.getQuote().getThought();
                    IsPublic isPublic = like.getQuote().getIsPublic();
                    LocalDateTime createdAt = like.getQuote().getCreatedAt();
                    LocalDateTime modifiedAt = like.getQuote().getModifiedAt();
                    LocalDateTime deletedAt = like.getQuote().getDeletedAt();
                    return LikeResponse.of(
                            id, quoteId, userId, nickname, title, author, category, pageNumber, sentence, thought, isPublic, createdAt, modifiedAt, deletedAt
                    );
                }
        ).toList();
        return new PageLikeResponse(modifiedContent, size, page, totalElements, totalPages);
    }
}
