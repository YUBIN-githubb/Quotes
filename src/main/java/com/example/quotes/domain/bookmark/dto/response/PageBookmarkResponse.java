package com.example.quotes.domain.bookmark.dto.response;

import com.example.quotes.common.enums.Category;
import com.example.quotes.domain.bookmark.entity.Bookmark;
import com.example.quotes.domain.like.dto.response.LikeResponse;
import lombok.Getter;

import java.util.List;

@Getter
public class PageBookmarkResponse {

    private final List<BookmarkResponse> content;
    private final int size;
    private final int page;
    private final long totalElements;
    private final int totalPages;

    private PageBookmarkResponse(List<BookmarkResponse> content, int size, int page, long totalElements, int totalPages) {
        this.content = content;
        this.size = size;
        this.page = page;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public static PageBookmarkResponse of(List<Bookmark> content, int size, int page, long totalElements, int totalPages) {

        List<BookmarkResponse> modifiedContent = content.stream().map(
                bookmark -> {
                    Long quoteId = bookmark.getQuote().getId();
                    String title = bookmark.getQuote().getTitle();
                    Category category = bookmark.getQuote().getCategory();
                    return BookmarkResponse.of(bookmark.getId(), quoteId, title, category);
                }
        ).toList();
        return new PageBookmarkResponse(modifiedContent, size, page, totalElements, totalPages);
    }
}
