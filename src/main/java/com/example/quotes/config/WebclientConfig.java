package com.example.quotes.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebclientConfig {

    @Value("${kakao.rest-api-key}")
    private String REST_API_KEY;

    @Bean
    public WebClient kakaoWebClient() {
        return WebClient.builder()
                .baseUrl("https://dapi.kakao.com")
                // restApiKey는 이 시점에 Spring에 의해 유효한 값이 주입된 상태임
                .defaultHeader("Authorization", REST_API_KEY) // 🔑 'KakaoAK ' 접두사도 확인해주세요!
                .build();
    }
}
