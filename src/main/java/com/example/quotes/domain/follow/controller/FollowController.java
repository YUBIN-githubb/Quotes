package com.example.quotes.domain.follow.controller;

import com.example.quotes.common.annotation.Auth;
import com.example.quotes.common.dto.AuthUser;
import com.example.quotes.domain.follow.dto.request.CreateFollowRequest;
import com.example.quotes.domain.follow.dto.response.CreateFollowResponse;
import com.example.quotes.domain.follow.entity.Follow;
import com.example.quotes.domain.follow.service.FollowCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class FollowController {

    private final FollowCommandService followCommandService;

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
}
