package com.example.quotes.domain.follow.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FollowerListResponse {

    private final Long id;
    private final Long followerId;
    private final String followerNickname;
    private final String followerProfileUrl;
    private final LocalDateTime createdAt;

    private FollowerListResponse(Long id, Long followerId, String followerNickname, String followerProfileUrl, LocalDateTime createdAt) {
        this.id = id;
        this.followerId = followerId;
        this.followerNickname = followerNickname;
        this.followerProfileUrl = followerProfileUrl;
        this.createdAt = createdAt;
    }

    public static FollowerListResponse of(Long id, Long followerId, String followerNickname, String followerProfileUrl, LocalDateTime createdAt) {
        return new FollowerListResponse(id, followerId, followerNickname, followerProfileUrl, createdAt);
    }
}
