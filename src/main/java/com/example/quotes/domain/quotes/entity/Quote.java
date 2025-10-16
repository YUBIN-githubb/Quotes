package com.example.quotes.domain.quotes.entity;

import com.example.quotes.common.entity.BaseEntity;
import com.example.quotes.common.enums.Category;
import com.example.quotes.common.enums.IsPublic;
import com.example.quotes.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "quotes")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Quote extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private Long pageNumber;

    @Column(nullable = false)
    private String sentence;

    @Column(nullable = false)
    private String thought;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private IsPublic isPublic;


    private Quote(User user, String title, String author, Category category, Long pageNumber, String sentence, String thought, IsPublic isPublic) {
        this.user = user;
        this.title = title;
        this.author = author;
        this.category = category;
        this.pageNumber = pageNumber;
        this.sentence = sentence;
        this.thought = thought;
        this.isPublic = isPublic;
    }

    public static Quote create(User user, String title, String author, Category category, Long pageNumber, String sentence, String thought, IsPublic isPublic) {
        return new Quote(user, title, author, category, pageNumber, sentence, thought, isPublic);
    }

    public void update(String title, String author, Category category, Long pageNumber, String sentence, String thought, IsPublic isPublic) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.pageNumber = pageNumber;
        this.sentence = sentence;
        this.thought = thought;
        this.isPublic = isPublic;
    }

    public void updateIsPublic(IsPublic isPublic) {
        this.isPublic = isPublic;
    }
}
