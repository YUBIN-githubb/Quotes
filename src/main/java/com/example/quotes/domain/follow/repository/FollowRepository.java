package com.example.quotes.domain.follow.repository;

import com.example.quotes.domain.follow.entity.Follow;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    Boolean existsByFollowerIdAndFolloweeId(Long followerId, Long followeeId);

    @EntityGraph(attributePaths = {"follower", "followee"})
    Follow findByFollowerIdAndFolloweeId(Long followerId, Long followeeId);
}
