package com.example.quotes.domain.like.controller;

import com.example.quotes.common.annotation.Auth;
import com.example.quotes.common.dto.AuthUser;
import com.example.quotes.domain.like.service.LikeCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LikeController {

    private final LikeCommandService likeCommandService;

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


}
