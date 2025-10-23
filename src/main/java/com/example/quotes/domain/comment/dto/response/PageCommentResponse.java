package com.example.quotes.domain.comment.dto.response;

import com.example.quotes.domain.comment.entity.Comment;
import com.example.quotes.domain.quote.dto.response.QuoteResponse;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PageCommentResponse {

    private final List<CommentResponse> content;
    private final int size;
    private final int page;
    private final long totalElements;
    private final int totalPages;

    private PageCommentResponse(List<CommentResponse> content, int size, int page, long totalElements, int totalPages) {
        this.content = content;
        this.size = size;
        this.page = page;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public static PageCommentResponse of(List<Comment> content, int size, int page, long totalElements, int totalPages) {
        List<CommentResponse> list = content.stream().map(
                comment -> {
                    Long id = comment.getId();
                    Long quoteId = comment.getQuote().getId();
                    Long userId = comment.getUser().getId();
                    String nickname = comment.getUser().getNickname();
                    String profileUrl = comment.getUser().getProfileUrl();
                    String contents = comment.getContents();
                    LocalDateTime createdAt = comment.getCreatedAt();
                    LocalDateTime modifiedAt = comment.getModifiedAt();
                    LocalDateTime deletedAt = comment.getDeletedAt();
                    return CommentResponse.of(id, quoteId, userId, nickname, profileUrl, contents, createdAt, modifiedAt, deletedAt);
                }
        ).toList();
        return new PageCommentResponse(list, size, page, totalElements, totalPages);
    }
}
