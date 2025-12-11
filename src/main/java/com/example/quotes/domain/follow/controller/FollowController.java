package com.example.quotes.domain.follow.controller;

import com.example.quotes.common.annotation.Auth;
import com.example.quotes.common.dto.AuthUser;
import com.example.quotes.domain.follow.dto.request.CreateFollowRequest;
import com.example.quotes.domain.follow.dto.response.CreateFollowResponse;
import com.example.quotes.domain.follow.dto.response.PageFolloweeListResponse;
import com.example.quotes.domain.follow.dto.response.PageFollowerListResponse;
import com.example.quotes.domain.follow.entity.Follow;
import com.example.quotes.domain.follow.service.FollowCommandService;
import com.example.quotes.domain.follow.service.FollowQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class FollowController {

    private final FollowCommandService followCommandService;
    private final FollowQueryService followQueryService;

    // 팔로우 생성
    @PostMapping("/follows")
    public ResponseEntity<CreateFollowResponse> createFollow(
            @Auth AuthUser authUser,
            @RequestBody CreateFollowRequest request) {
        Follow follow = followCommandService.createFollow(authUser.getUserId(), request.getFolloweeId());
        CreateFollowResponse createFollowResponse = CreateFollowResponse.of(follow.getId(), follow.getFollower().getId(), follow.getFollowee().getId(), follow.getCreatedAt());
        return ResponseEntity.ok(createFollowResponse);
    }

    // 팔로우 삭제
    @DeleteMapping("/follows/{followId}")
    public ResponseEntity<Void> deleteFollow(
            @PathVariable Long followId) {
        followCommandService.deleteFollow(followId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 팔로워 조회
    @GetMapping("/users/{userId}/followers")
    public ResponseEntity<PageFollowerListResponse> findFollowers(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<Follow> followers = followQueryService.findFollowers(userId, size, page);
        PageFollowerListResponse pageFollowerListResponse = PageFollowerListResponse.of(followers.getContent(), followers.getSize(), followers.getNumber(), followers.getTotalElements(), followers.getTotalPages());
        return ResponseEntity.ok(pageFollowerListResponse);
    }


    @GetMapping("/users/{userId}/followings")
    public ResponseEntity<PageFolloweeListResponse> findFollowees(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<Follow> followees = followQueryService.findFollowees(userId, size, page);
        PageFolloweeListResponse pageFolloweeListResponse = PageFolloweeListResponse.of(followees.getContent(), followees.getSize(), followees.getNumber(), followees.getTotalElements(), followees.getTotalPages());
        return ResponseEntity.ok(pageFolloweeListResponse);
    }
}
