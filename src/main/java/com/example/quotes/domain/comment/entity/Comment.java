package com.example.quotes.domain.comment.entity;

import com.example.quotes.common.entity.BaseEntity;
import com.example.quotes.domain.quote.entity.Quote;
import com.example.quotes.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comments")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quote_id", nullable = false)
    private Quote quote;

    @Column(nullable = false)
    private String contents;

    private Comment(User user, Quote quote, String contents) {
        this.user = user;
        this.quote = quote;
        this.contents = contents;
    }

    public static Comment create(User user, Quote quote, String contents) {
        return new Comment(user, quote, contents);
    }

    public void updateComment(String contents) {
        this.contents = contents;
    }
}
