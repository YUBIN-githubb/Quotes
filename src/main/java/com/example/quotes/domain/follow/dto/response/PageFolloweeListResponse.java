package com.example.quotes.domain.follow.dto.response;

import com.example.quotes.domain.follow.entity.Follow;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PageFolloweeListResponse {

    private final List<FolloweeListResponse> content;
    private final int size;
    private final int page;
    private final long totalElements;
    private final int totalPages;

    private PageFolloweeListResponse (List<FolloweeListResponse> content, int size, int page, long totalElements, int totalPages) {
        this.content = content;
        this.size = size;
        this.page = page;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public static PageFolloweeListResponse of (List<Follow> content, int size, int page, long totalElements, int totalPages) {
        List<FolloweeListResponse> modifiedContent = content.stream().map(
                follow -> {
                    Long id = follow.getId();
                    Long followeeId = follow.getFollowee().getId();
                    String followeeNickname = follow.getFollowee().getNickname();
                    String followeeProfileUrl = follow.getFollowee().getProfileUrl();
                    LocalDateTime createdAt = follow.getCreatedAt();
                    return FolloweeListResponse.of(id, followeeId, followeeNickname, followeeProfileUrl, createdAt);
                }
        ).toList();
        return new PageFolloweeListResponse(modifiedContent, size, page, totalElements, totalPages);
    }
}
