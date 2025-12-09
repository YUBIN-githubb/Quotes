package com.example.quotes.domain.follow.service;

import com.example.quotes.domain.follow.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FollowQueryService {

    private final FollowRepository followRepository;

    public Boolean existByFollowerIdAndFolloweeId(Long followerId, Long followeeId) {
        return followRepository.existsByFollowerIdAndFolloweeId(followerId, followeeId);
    }
}
