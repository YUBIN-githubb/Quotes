package com.example.quotes.domain.book.dto.response;

import com.example.quotes.common.dto.PageResponse;
import com.example.quotes.domain.book.entity.Book;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class PageBookResponse {

    private final List<Book> content;
    private final int size;
    private final int page;
    private final long totalElements;
    private final int totalPages;

    private PageBookResponse(List<Book> content, int size, int page, long totalElements, int totalPages) {
        this.content = content;
        this.size = size;
        this.page = page;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public static PageBookResponse of(List<Book> content, int size, int page, long totalElements, int totalPages) {
        return new PageBookResponse(content, size, page, totalElements, totalPages);
    }
}
