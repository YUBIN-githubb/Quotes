package com.example.quotes.domain.like.dto.response;

import com.example.quotes.common.enums.Category;
import com.example.quotes.common.enums.IsPublic;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class LikeResponse {

    private final Long id;
    private final Long quoteId;
    private final Long userId;
    private final String nickname;
    private final String title;
    private final String author;
    private final Category category;
    private final Long pageNumber;
    private final String sentence;
    private final String thought;
    private final IsPublic isPublic;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final LocalDateTime deletedAt;
    private final Long likeCount;

    private LikeResponse(
            Long id,
            Long quoteId,
            Long userId,
            String nickname,
            String title,
            String author,
            Category category,
            Long pageNumber,
            String sentence,
            String thought,
            IsPublic isPublic,
            LocalDateTime createdAt,
            LocalDateTime modifiedAt,
            LocalDateTime deletedAt,
            Long likeCount
    ) {
        this.id = id;
        this.quoteId = quoteId;
        this.userId = userId;
        this.nickname = nickname;
        this.title = title;
        this.author = author;
        this.category = category;
        this.pageNumber = pageNumber;
        this.sentence = sentence;
        this.thought = thought;
        this.isPublic = isPublic;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.deletedAt = deletedAt;
        this.likeCount = likeCount;
    }

    public static LikeResponse of(
            Long id,
            Long quoteId,
            Long userId,
            String nickname,
            String title,
            String author,
            Category category,
            Long pageNumber,
            String sentence,
            String thought,
            IsPublic isPublic,
            LocalDateTime createdAt,
            LocalDateTime modifiedAt,
            LocalDateTime deletedAt,
            Long likeCount) {
        return new LikeResponse(id, quoteId, userId, nickname, title, author, category, pageNumber, sentence, thought, isPublic, createdAt, modifiedAt, deletedAt, likeCount);
    }
}
