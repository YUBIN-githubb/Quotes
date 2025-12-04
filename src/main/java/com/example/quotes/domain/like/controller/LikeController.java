package com.example.quotes.domain.like.controller;

import com.example.quotes.common.annotation.Auth;
import com.example.quotes.common.dto.AuthUser;
import com.example.quotes.domain.like.dto.response.CreateLikeResponse;
import com.example.quotes.domain.like.dto.response.LikeResponse;
import com.example.quotes.domain.like.entity.Like;
import com.example.quotes.domain.like.service.LikeCommandService;
import com.example.quotes.domain.like.service.LikeQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LikeController {

    private final LikeCommandService likeCommandService;
    private final LikeQueryService likeQueryService;

    @PostMapping("/quotes/{quoteId}/likes")
    public ResponseEntity<CreateLikeResponse> createLike(
            @Auth AuthUser authUser,
            @PathVariable Long quoteId) {
        Like like = likeCommandService.createLike(authUser.getUserId(), quoteId);
        CreateLikeResponse createLikeResponse = CreateLikeResponse.of(like.getId(), like.getUser().getId(), like.getQuote().getId());
        return ResponseEntity.ok(createLikeResponse);
    }

    @DeleteMapping("/quotes/{quoteId}/likes/{likeId}")
    public ResponseEntity<Void> deleteLike(
            @Auth AuthUser authUser,
            @PathVariable Long quoteId,
            @PathVariable Long likeId) {
        likeCommandService.deleteLike(authUser.getUserId(), quoteId, likeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/likes")
    public ResponseEntity<Page<LikeResponse>> getLikes(
            @Auth AuthUser authUser,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<Like> likes = likeQueryService.getLikes(authUser.getUserId(), page, size);
        Page<LikeResponse> pageLikeResponse = likes.map(
                like -> LikeResponse.of(
                        like.getId(),
                        like.getQuote().getId(),
                        like.getUser().getId(),
                        like.getUser().getNickname(),
                        like.getQuote().getTitle(),
                        like.getQuote().getAuthor(),
                        like.getQuote().getCategory(),
                        like.getQuote().getPageNumber(),
                        like.getQuote().getSentence(),
                        like.getQuote().getThought(),
                        like.getQuote().getIsPublic(),
                        like.getQuote().getCreatedAt(),
                        like.getQuote().getModifiedAt(),
                        like.getQuote().getDeletedAt(),
                        likeQueryService.countLikes(like.getQuote().getId())
                ));
        return ResponseEntity.ok(pageLikeResponse);
    }
}
