package com.example.quotes.domain.follow.service;

import com.example.quotes.domain.follow.entity.Follow;
import com.example.quotes.domain.follow.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public Page<Follow> findFollowers(Long userId, int size, int page) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return followRepository.findByFolloweeId(userId, pageable);
    }

    public Page<Follow> findFollowees(Long userId, int size, int page) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return followRepository.findByFollowerId(userId, pageable);
    }
}
