package com.example.quotes.domain.comment.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponse {

    private final Long id;
    private final Long quoteId;
    private final Long userId;
    private final String nickname;
    private final String profileUrl;
    private final String contents;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final LocalDateTime deletedAt;

    private CommentResponse(Long id, Long quoteId, Long userId, String nickname, String profileUrl, String contents, LocalDateTime createdAt, LocalDateTime modifiedAt, LocalDateTime deletedAt) {
        this.id = id;
        this.quoteId = quoteId;
        this.userId = userId;
        this.nickname = nickname;
        this.profileUrl = profileUrl;
        this.contents = contents;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.deletedAt = deletedAt;
    }

    public static CommentResponse of(Long id, Long quoteId, Long userId, String nickname, String profileUrl, String contents, LocalDateTime createdAt, LocalDateTime modifiedAt, LocalDateTime deletedAt) {
        return new CommentResponse(id, quoteId, userId, nickname, profileUrl, contents, createdAt, modifiedAt, deletedAt);
    }
}
