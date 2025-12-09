package com.example.quotes.domain.follow.dto.request;

import lombok.Getter;

@Getter
public class CreateFollowRequest {

    private final Long followeeId;

    private CreateFollowRequest(Long followeeId) {
        this.followeeId = followeeId;
    }

    public static CreateFollowRequest of(Long followeeId) {
        return new CreateFollowRequest(followeeId);
    }
}
