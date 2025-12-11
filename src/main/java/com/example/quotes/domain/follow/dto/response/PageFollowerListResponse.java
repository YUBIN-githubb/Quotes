package com.example.quotes.domain.follow.dto.response;

import com.example.quotes.domain.follow.entity.Follow;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PageFollowerListResponse {

    private final List<FollowerListResponse> content;
    private final int size;
    private final int page;
    private final long totalElements;
    private final int totalPages;

    private PageFollowerListResponse(List<FollowerListResponse> content, int size, int page, long totalElements, int totalPages) {
        this.content = content;
        this.size = size;
        this.page = page;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public static PageFollowerListResponse of (List<Follow> content, int size, int page, long totalElements, int totalPages) {
        List<FollowerListResponse> modifiedContent = content.stream().map(
                follow -> {
                    Long id = follow.getId();
                    Long followerId = follow.getFollower().getId();
                    String followerNickname = follow.getFollower().getNickname();
                    String followerProfileUrl = follow.getFollower().getProfileUrl();
                    LocalDateTime createdAt = follow.getCreatedAt();
                    return FollowerListResponse.of(id, followerId, followerNickname, followerProfileUrl, createdAt);
                }
        ).toList();
        return new PageFollowerListResponse(modifiedContent, size, page, totalElements, totalPages);
    }
}
