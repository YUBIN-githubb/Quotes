package com.example.quotes.domain.book.controller;

import com.example.quotes.domain.book.client.KakaoBookClient;
import com.example.quotes.domain.book.dto.response.KakaoBookSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.awt.print.Book;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final KakaoBookClient kakaoBookClient;

    @GetMapping("/books")
    public ResponseEntity<KakaoBookSearchResponse> getBooks(
            @RequestParam String query,
            @RequestParam(defaultValue = "1") int page) {

        KakaoBookSearchResponse kakaoBookSearchResponse = kakaoBookClient.searchBooks(query,page);
        return ResponseEntity.ok(kakaoBookSearchResponse);
    }
}
