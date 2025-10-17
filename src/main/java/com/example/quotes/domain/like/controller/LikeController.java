package com.example.quotes.domain.like.controller;

import com.example.quotes.common.annotation.Auth;
import com.example.quotes.common.dto.AuthUser;
import com.example.quotes.common.enums.Category;
import com.example.quotes.domain.like.dto.response.LikeResponse;
import com.example.quotes.domain.like.dto.response.PageLikeResponse;
import com.example.quotes.domain.like.entity.Like;
import com.example.quotes.domain.like.service.LikeCommandService;
import com.example.quotes.domain.like.service.LikeQueryService;
import com.example.quotes.domain.quote.entity.Quote;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LikeController {

    private final LikeCommandService likeCommandService;
    private final LikeQueryService likeQueryService;

    @PostMapping("/quotes/{quoteId}/likes")
    public ResponseEntity<Void> createLike(
            @Auth AuthUser authUser,
            @PathVariable Long quoteId) {
        likeCommandService.createLike(authUser, quoteId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/quotes/{quoteId}/likes/{likeId}")
    public ResponseEntity<Void> deleteLike(
            @Auth AuthUser authUser,
            @PathVariable Long quoteId,
            @PathVariable Long likeId) {
        likeCommandService.deleteLike(authUser, quoteId, likeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/likes")
    public ResponseEntity<PageLikeResponse> getLikes(
            @Auth AuthUser authUser,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<Like> likes = likeQueryService.getLikes(authUser, page, size);
        PageLikeResponse pageLikeResponse = PageLikeResponse.of(likes.getContent(), likes.getSize(), likes.getNumber(), likes.getTotalElements(), likes.getTotalPages());
        return ResponseEntity.ok(pageLikeResponse);
    }
}
