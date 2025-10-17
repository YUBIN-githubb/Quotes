package com.example.quotes.domain.book.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class KakaoBookDocument {
    private String title;
    private List<String> authors;
    private String publisher;
    private String isbn;
    private String thumbnail;
}
