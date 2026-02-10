package com.example.quotes.domain.user.controller;

import com.example.quotes.common.annotation.Auth;
import com.example.quotes.common.dto.AuthUser;
import com.example.quotes.domain.like.service.LikeCountCacheService;
import com.example.quotes.domain.like.service.LikeQueryService;
import com.example.quotes.domain.quote.dto.response.PageQuoteResponse;
import com.example.quotes.domain.quote.dto.response.QuoteFeedResponse;
import com.example.quotes.domain.quote.dto.response.QuoteProfileResponse;
import com.example.quotes.domain.quote.dto.response.QuoteResponse;
import com.example.quotes.domain.quote.entity.Quote;
import com.example.quotes.domain.quote.service.QuoteQueryService;
import com.example.quotes.domain.user.dto.request.UpdatePasswordRequest;
import com.example.quotes.domain.user.dto.request.UpdateUserRequest;
import com.example.quotes.domain.user.dto.request.WithdrawUserRequest;
import com.example.quotes.domain.follow.service.FollowQueryService;
import com.example.quotes.domain.user.dto.response.TopUserResponse;
import com.example.quotes.domain.user.dto.response.UserResponse;
import com.example.quotes.domain.user.entity.User;
import com.example.quotes.domain.user.service.UserCommandService;
import com.example.quotes.domain.user.service.UserQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserQueryService userQueryService;
    private final UserCommandService userCommandService;
    private final QuoteQueryService quoteQueryService;
    private final LikeQueryService likeQueryService;
    private final LikeCountCacheService likeCountCacheService;
    private final FollowQueryService followQueryService;

    @GetMapping("/users")
    public ResponseEntity<UserResponse> getUser(@Auth AuthUser authUser) {

        User user = userQueryService.getUserById(authUser.getUserId());
        UserResponse userResponse = UserResponse.of(user.getId(), user.getEmail(), user.getUserRole(), user.getProfileUrl(), user.getNickname());
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/users/top")
    public ResponseEntity<List<TopUserResponse>> getTopUsers() {
        List<TopUserResponse> topUsers = followQueryService.findTopUsersByFollowerCount();
        return ResponseEntity.ok(topUsers);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserResponse> getUserById(
            @PathVariable Long userId) {
        User user = userQueryService.getUserById(userId);
        UserResponse userResponse = UserResponse.of(user.getId(),user.getEmail(), user.getUserRole(), user.getProfileUrl(), user.getNickname());
        return ResponseEntity.ok(userResponse);
    }

    @PutMapping("/users")
    public ResponseEntity<UserResponse> updateUser(
            @Auth AuthUser authUser,
            @Valid @RequestBody UpdateUserRequest request) {

        User user = userCommandService.updateUser(authUser.getUserId(), request.getProfileUrl(), request.getNickname());
        UserResponse userResponse = UserResponse.of(user.getId(),user.getEmail(), user.getUserRole(), user.getProfileUrl(), user.getNickname());
        return ResponseEntity.ok(userResponse);
    }

    @PatchMapping("/users")
    public ResponseEntity<String> updatePassword(
            @Auth AuthUser authUser,
            @Valid @RequestBody UpdatePasswordRequest request) {

        userCommandService.updatePassword(authUser.getUserId(), request.getOldPassword(), request.getNewPassword());
        return ResponseEntity.ok("비밀번호가 성공적으로 업데이트 되었습니다.");
    }

    @DeleteMapping("/users")
    public ResponseEntity<String> deleteUser(
            @Auth AuthUser authUser,
            @Valid @RequestBody WithdrawUserRequest request) {

        userCommandService.withdrawUser(authUser.getUserId(), request.getPassword());
        return ResponseEntity.ok("회원탈퇴가 성공적으로 되었습니다.");
    }

    @GetMapping("/users/me/quotes")
    public ResponseEntity<Page<QuoteProfileResponse>> getQuotes(
            @Auth AuthUser authUser,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<Quote> myQuotes = quoteQueryService.getMyQuotes(authUser.getUserId(), page, size);
        Map<Long, Boolean> myLikeMap = likeQueryService.getMyLikeMap(authUser.getUserId(), myQuotes.getContent());
        Page<QuoteProfileResponse> response = myQuotes.map(quote -> {
            QuoteResponse basicDto = QuoteResponse.of(
                    quote.getId(),
                    quote.getUser().getId(),
                    quote.getUser().getNickname(),
                    quote.getTitle(),
                    quote.getAuthor(),
                    quote.getCategory(),
                    quote.getPageNumber(),
                    quote.getSentence(),
                    quote.getThought(),
                    quote.getIsPublic(),
                    quote.getCreatedAt(),
                    quote.getModifiedAt(),
                    quote.getDeletedAt()
            );
            boolean isLiked = Boolean.TRUE.equals(myLikeMap.get(quote.getId()));
            Long likeCount = likeCountCacheService.getLikeCount(quote.getId());
            return QuoteProfileResponse.of(basicDto, isLiked, likeCount);
        });
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users/{userId}/quotes")
    public ResponseEntity<Page<QuoteProfileResponse>> getQuotesByUser(
            @Auth AuthUser authUser,
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Quote> quotes = quoteQueryService.getQuoteByUserId(userId,page,size);
        Map<Long, Boolean> myLikeMap = likeQueryService.getMyLikeMap(authUser.getUserId(), quotes.getContent());
        Page<QuoteProfileResponse> response = quotes.map(quote -> {
            QuoteResponse basicDto = QuoteResponse.of(
                    quote.getId(),
                    quote.getUser().getId(),
                    quote.getUser().getNickname(),
                    quote.getTitle(),
                    quote.getAuthor(),
                    quote.getCategory(),
                    quote.getPageNumber(),
                    quote.getSentence(),
                    quote.getThought(),
                    quote.getIsPublic(),
                    quote.getCreatedAt(),
                    quote.getModifiedAt(),
                    quote.getDeletedAt()
            );
            boolean isLiked = Boolean.TRUE.equals(myLikeMap.get(quote.getId()));
            Long likeCount = likeCountCacheService.getLikeCount(quote.getId());
            return QuoteProfileResponse.of(basicDto, isLiked, likeCount);
        });
        return ResponseEntity.ok(response);
    }
}
