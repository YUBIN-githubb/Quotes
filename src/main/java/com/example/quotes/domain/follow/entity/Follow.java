package com.example.quotes.domain.follow.entity;

import com.example.quotes.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "follows")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_id", nullable = false)
    private User follower; // 팔로우 신청 하는 사람

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "followee_id", nullable = false)
    private User followee; // 팔로우 신청 받는 사람

    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    private Follow (User follower, User followee, LocalDateTime createdAt) {
        this.follower = follower;
        this.followee = followee;
        this.createdAt = createdAt;
    }

    public static Follow create(User follower, User followee, LocalDateTime createdAt) {
        return new Follow(follower, followee, createdAt);
    }
}
