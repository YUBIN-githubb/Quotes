package com.example.quotes.domain.follow.dto.response;

import com.example.quotes.domain.follow.entity.Follow;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateFollowResponse {

    private final Long id;
    private final Long followerId;
    private final Long followeeId;
    private final LocalDateTime createdAt;

    private CreateFollowResponse(Long id, Long followerId, Long followeeId, LocalDateTime createdAt) {
        this.id = id;
        this.followerId = followerId;
        this.followeeId = followeeId;
        this.createdAt = createdAt;
    }

    public static CreateFollowResponse of(Long id, Long followerId, Long followeeId, LocalDateTime createdAt) {
        return new CreateFollowResponse(id, followerId, followeeId, createdAt);
    }
}
