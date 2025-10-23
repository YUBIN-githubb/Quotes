package com.example.quotes.domain.comment.controller;

import com.example.quotes.common.annotation.Auth;
import com.example.quotes.common.dto.AuthUser;
import com.example.quotes.domain.comment.dto.request.CreateCommentRequest;
import com.example.quotes.domain.comment.dto.request.UpdateCommentRequest;
import com.example.quotes.domain.comment.dto.response.CommentResponse;
import com.example.quotes.domain.comment.dto.response.PageCommentResponse;
import com.example.quotes.domain.comment.entity.Comment;
import com.example.quotes.domain.comment.service.CommentCommandService;
import com.example.quotes.domain.comment.service.CommentQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentQueryService commentQueryService;
    private final CommentCommandService commentCommandService;

    @GetMapping("/quotes/{quoteId}/comments")
    public ResponseEntity<PageCommentResponse> getComments(
            @Auth AuthUser authUser,
            @PathVariable Long quoteId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Comment> comments = commentQueryService.getComments(quoteId, pageable);
        PageCommentResponse pageCommentResponse = PageCommentResponse.of(comments.getContent(), comments.getSize(), comments.getNumber(), comments.getTotalElements(), comments.getTotalPages());
        return ResponseEntity.ok(pageCommentResponse);
    }

    @PostMapping("/quotes/{quoteId}/comments")
    public ResponseEntity<CommentResponse> createComment(
            @Auth AuthUser authUser,
            @PathVariable Long quoteId,
            @Valid @RequestBody CreateCommentRequest request) {

        Comment comment = commentCommandService.createComment(authUser.getUserId(), quoteId, request.getContents());
        CommentResponse commentResponse = CommentResponse.of(
                comment.getId(),
                comment.getQuote().getId(),
                comment.getUser().getId(),
                comment.getUser().getNickname(),
                comment.getUser().getProfileUrl(),
                comment.getContents(),
                comment.getCreatedAt(),
                comment.getModifiedAt(),
                comment.getDeletedAt());
        return ResponseEntity.ok(commentResponse);
    }

    @PatchMapping("/quotes/{quoteId}/comments/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(
            @Auth AuthUser authUser,
            @PathVariable Long quoteId,
            @PathVariable Long commentId,
            @Valid @RequestBody UpdateCommentRequest request) {

        Comment comment = commentCommandService.updateComment(authUser.getUserId(), quoteId, commentId, request.getContents());
        CommentResponse commentResponse = CommentResponse.of(
                comment.getId(),
                comment.getQuote().getId(),
                comment.getUser().getId(),
                comment.getUser().getNickname(),
                comment.getUser().getProfileUrl(),
                comment.getContents(),
                comment.getCreatedAt(),
                comment.getModifiedAt(),
                comment.getDeletedAt());
        return ResponseEntity.ok(commentResponse);
    }

    @DeleteMapping("/quotes/{quoteId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @Auth AuthUser authUser,
            @PathVariable Long quoteId,
            @PathVariable Long commentId) {

        commentCommandService.deleteComment(authUser.getUserId(), quoteId, commentId);
        return ResponseEntity.ok().build();
    }
}
