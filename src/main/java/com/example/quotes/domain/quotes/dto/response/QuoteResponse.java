package com.example.quotes.domain.quotes.dto.response;

import com.example.quotes.common.enums.Category;
import com.example.quotes.common.enums.IsPublic;
import jakarta.persistence.Column;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
public class QuoteResponse {

    private final Long id;
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

    private QuoteResponse(
            Long id,
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
            LocalDateTime deletedAt) {
        this.id = id;
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
    }

    public static QuoteResponse of(
            Long id,
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
            LocalDateTime deletedAt) {
        return new QuoteResponse(id, userId, nickname, title, author, category, pageNumber, sentence, thought, isPublic, createdAt, modifiedAt, deletedAt);
    }
}
