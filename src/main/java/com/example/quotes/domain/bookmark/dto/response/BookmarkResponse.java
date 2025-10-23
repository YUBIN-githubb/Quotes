package com.example.quotes.domain.bookmark.dto.response;

import com.example.quotes.common.enums.Category;
import lombok.Getter;

@Getter
public class BookmarkResponse {

    private final Long id;
    private final Long quoteId;
    private final String title;
    private final Category category;

    private BookmarkResponse(Long id, Long quoteId, String title, Category category) {
        this.id = id;
        this.quoteId = quoteId;
        this.title = title;
        this.category = category;
    }

    public static BookmarkResponse of(Long bookmarkId, Long quoteId, String title, Category category) {
        return new BookmarkResponse(bookmarkId, quoteId, title, category);
    }
}
