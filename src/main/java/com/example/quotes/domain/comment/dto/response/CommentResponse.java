package com.example.quotes.domain.comment.dto.response;

import lombok.Getter;

@Getter
public class CommentResponse {

    private final Long id;
    private final Long quoteId;
    private final Long userId;
    private final String nickname;
    private final String profileUrl;
    private final String contents;

    private CommentResponse(Long id, Long quoteId, Long userId, String nickname, String profileUrl, String contents) {
        this.id = id;
        this.quoteId = quoteId;
        this.userId = userId;
        this.nickname = nickname;
        this.profileUrl = profileUrl;
        this.contents = contents;
    }

    public static CommentResponse of(Long id, Long quoteId, Long userId, String nickname, String profileUrl, String contents) {
        return new CommentResponse(id, quoteId, userId, nickname, profileUrl, contents);
    }
}
