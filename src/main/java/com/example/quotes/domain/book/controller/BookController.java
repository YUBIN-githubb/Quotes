package com.example.quotes.domain.book.controller;

import com.example.quotes.common.annotation.Auth;
import com.example.quotes.common.dto.AuthUser;
import com.example.quotes.domain.book.dto.response.PageBookResponse;
import com.example.quotes.domain.book.entity.Book;
import com.example.quotes.domain.book.service.BookQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookQueryService bookQueryService;

    @GetMapping("/books")
    public ResponseEntity<PageBookResponse> getBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Book> books = bookQueryService.getBooks(page, size);
        PageBookResponse pageBookResponse = PageBookResponse.of(books.getContent(), books.getSize(), books.getNumber(), books.getTotalElements(), books.getTotalPages());
        return ResponseEntity.ok(pageBookResponse);
    }
}
