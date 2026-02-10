package com.example.quotes.domain.user.dto.response;

import lombok.Getter;

@Getter
public class TopUserResponse {

    private final Long userId;
    private final String nickname;
    private final String profileUrl;
    private final Long followerCount;

    private TopUserResponse(Long userId, String nickname, String profileUrl, Long followerCount) {
        this.userId = userId;
        this.nickname = nickname;
        this.profileUrl = profileUrl;
        this.followerCount = followerCount;
    }

    public static TopUserResponse of(Long userId, String nickname, String profileUrl, Long followerCount) {
        return new TopUserResponse(userId, nickname, profileUrl, followerCount);
    }
}
