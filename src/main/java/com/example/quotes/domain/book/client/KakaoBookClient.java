package com.example.quotes.domain.book.client;

import com.example.quotes.config.WebclientConfig;
import com.example.quotes.domain.book.dto.response.KakaoBookSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class KakaoBookClient {

    private final WebclientConfig webclientConfig;

    public KakaoBookSearchResponse searchBooks(String query, Integer page) {
        return webclientConfig.kakaoWebClient().get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v3/search/book")
                        .queryParam("query", query)
                        .queryParam("page", page)
                        .build())
                .retrieve()
                .bodyToMono(KakaoBookSearchResponse.class)
                .block();
    }
}
