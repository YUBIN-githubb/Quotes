package com.example.quotes.domain.follow.service;

import com.example.quotes.domain.follow.entity.Follow;
import com.example.quotes.domain.follow.repository.FollowRepository;
import com.example.quotes.domain.user.dto.response.TopUserResponse;
import com.example.quotes.domain.user.entity.User;
import com.example.quotes.domain.user.service.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FollowQueryService {

    private final FollowRepository followRepository;
    private final UserQueryService userQueryService;

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

    public List<Long> findFollowerIds(Long userId) {
        List<Follow> followers = followRepository.findByFolloweeId(userId);
        return followers.stream().map(
                f -> {
                    return f.getFollower().getId();
                }
        ).toList();
    }

    public List<TopUserResponse> findTopUsersByFollowerCount() {
        List<Object[]> topFollowees = followRepository.findTopFolloweeIds(PageRequest.ofSize(10));

        Map<Long, Long> followerCountMap = topFollowees.stream()
                .collect(Collectors.toMap(
                        row -> (Long) row[0],
                        row -> (Long) row[1]
                ));

        return topFollowees.stream()
                .map(row -> {
                    Long userId = (Long) row[0];
                    User user = userQueryService.getUserById(userId);
                    return TopUserResponse.of(user.getId(), user.getNickname(), user.getProfileUrl(), followerCountMap.get(userId));
                })
                .toList();
    }
}
