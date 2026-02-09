package com.example.quotes.domain.newsfeed.controller;

import com.example.quotes.common.annotation.Auth;
import com.example.quotes.common.dto.AuthUser;
import com.example.quotes.domain.newsfeed.dto.response.NewsfeedPageResponse;
import com.example.quotes.domain.newsfeed.service.NewsfeedQueryService;
import com.example.quotes.domain.quote.dto.response.QuoteFeedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NewsfeedController {

    private final NewsfeedQueryService newsfeedQueryService;

    @GetMapping("/newsfeed")
    public ResponseEntity<NewsfeedPageResponse> getNewsfeed(
            @Auth AuthUser authUser,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        List<QuoteFeedResponse> content = newsfeedQueryService.getNewsfeed(authUser.getUserId(), page, size);
        long totalElements = newsfeedQueryService.getNewsfeedSize(authUser.getUserId());

        NewsfeedPageResponse response = NewsfeedPageResponse.of(content, page, size, totalElements);
        return ResponseEntity.ok(response);
    }
}
