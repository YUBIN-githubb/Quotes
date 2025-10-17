package com.example.quotes.domain.like.entity;

import com.example.quotes.common.entity.BaseEntity;
import com.example.quotes.domain.quote.entity.Quote;
import com.example.quotes.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "likes")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quote_id", nullable = false)
    private Quote quote;

    private Like(User user, Quote quote) {
        this.user = user;
        this.quote = quote;
    }

    public static Like create(User user, Quote quote) {
        return new Like(user, quote);
    }
}
