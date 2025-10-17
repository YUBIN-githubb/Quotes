package com.example.quotes.domain.book.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class KakaoBookSearchResponse {
    private KakaoBookMeta meta;
    private List<KakaoBookDocument> documents;
}
