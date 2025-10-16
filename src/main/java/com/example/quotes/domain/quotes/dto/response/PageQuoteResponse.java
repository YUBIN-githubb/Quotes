package com.example.quotes.domain.quotes.dto.response;

import com.example.quotes.common.dto.PageResponse;
import com.example.quotes.common.enums.Category;
import com.example.quotes.common.enums.IsPublic;
import com.example.quotes.domain.quotes.entity.Quote;
import lombok.Getter;

import java.awt.print.Book;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PageQuoteResponse {

    private final List<QuoteResponse> content;
    private final int size;
    private final int page;
    private final long totalElements;
    private final int totalPages;

    private PageQuoteResponse(List<QuoteResponse> content, int size, int page, long totalElements, int totalPages) {
        this.content = content;
        this.size = size;
        this.page = page;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public static PageQuoteResponse of(List<Quote> content, int size, int page, long totalElements, int totalPages) {

        List<QuoteResponse> modifiedContent = content.stream().map(
                quote -> {
                    Long id = quote.getId();
                    Long userId = quote.getUser().getId();
                    String nickname = quote.getUser().getNickname();
                    String title = quote.getTitle();
                    String author = quote.getAuthor();
                    Category category = quote.getCategory();
                    Long pageNumber = quote.getPageNumber();
                    String sentence = quote.getSentence();
                    String thought = quote.getThought();
                    IsPublic isPublic = quote.getIsPublic();
                    LocalDateTime createdAt = quote.getCreatedAt();
                    LocalDateTime modifiedAt = quote.getModifiedAt();
                    LocalDateTime deletedAt = quote.getDeletedAt();
                    return QuoteResponse.of(id, userId, nickname, title, author, category, pageNumber, sentence, thought, isPublic, createdAt, modifiedAt, deletedAt);
                }).collect(Collectors.toList());
        return new PageQuoteResponse(modifiedContent, size, page, totalElements, totalPages);
    }
}
