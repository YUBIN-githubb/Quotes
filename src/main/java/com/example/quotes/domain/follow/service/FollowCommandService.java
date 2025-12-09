package com.example.quotes.domain.follow.service;

import com.example.quotes.common.exceptions.CustomException;
import com.example.quotes.domain.follow.entity.Follow;
import com.example.quotes.domain.follow.repository.FollowRepository;
import com.example.quotes.domain.user.entity.User;
import com.example.quotes.domain.user.service.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional
public class FollowCommandService {

    private final FollowRepository followRepository;
    private final FollowQueryService followQueryService;
    private final UserQueryService userQueryService;

    public Follow createFollow(Long followerId, Long followeeId) {
        if (!followQueryService.existByFollowerIdAndFolloweeId(followerId, followeeId)) {
            User follower = userQueryService.getUserById(followerId);
            User followee = userQueryService.getUserById(followeeId);
            Follow follow = Follow.create(follower, followee, LocalDateTime.now());
            return followRepository.save(follow);
        } else {
            throw new CustomException(BAD_REQUEST, "이미 팔로우 신청이 완료되었습니다.");
        }
    }

    public void deleteFollow(Long followId) {

        Follow follow = followRepository.findById(followId).orElseThrow(
                () -> new CustomException(NOT_FOUND, "해당 팔로우 관계가 존재하지 않습니다.")
        );

        followRepository.delete(follow);
    }
}
