package com.example.quotes.domain.follow.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FolloweeListResponse {

    private final Long id;
    private final Long followeeId;
    private final String followeeNickname;
    private final String followeeProfileUrl;
    private final LocalDateTime createdAt;

    private FolloweeListResponse(Long id, Long followeeId, String followeeNickname, String followeeProfileUrl, LocalDateTime createdAt) {
        this.id = id;
        this.followeeId = followeeId;
        this.followeeNickname = followeeNickname;
        this.followeeProfileUrl = followeeProfileUrl;
        this.createdAt = createdAt;
    }

    public static FolloweeListResponse of(Long id, Long followeeId, String followeeNickname, String followeeProfileUrl, LocalDateTime createdAt) {
        return new FolloweeListResponse(id, followeeId, followeeNickname, followeeProfileUrl, createdAt);
    }
}
