package com.example.quotes.domain.bookmark.entity;

import com.example.quotes.common.entity.BaseEntity;
import com.example.quotes.domain.quote.entity.Quote;
import com.example.quotes.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "bookmarks")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bookmark extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quote_id", nullable = false)
    private Quote quote;

    private Bookmark(User user, Quote quote) {
        this.user = user;
        this.quote = quote;
    }

    public static Bookmark create(User user, Quote quote) {
        return new Bookmark(user, quote);
    }
}
