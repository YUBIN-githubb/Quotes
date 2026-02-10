package com.example.quotes.domain.follow.repository;

import com.example.quotes.domain.follow.entity.Follow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    Boolean existsByFollowerIdAndFolloweeId(Long followerId, Long followeeId);

    @EntityGraph(attributePaths = {"follower", "followee"})
    Follow findByFollowerIdAndFolloweeId(Long followerId, Long followeeId);

    @EntityGraph(attributePaths = {"follower"})
    Page<Follow> findByFolloweeId(Long followeeId, Pageable pageable);

    @EntityGraph(attributePaths = {"followee"})
    Page<Follow> findByFollowerId(Long followerId, Pageable pageable);

    @EntityGraph(attributePaths = {"follower"})
    List<Follow> findByFolloweeId (Long followeeId);

    @Query("SELECT f.followee.id, COUNT(f) FROM Follow f GROUP BY f.followee.id ORDER BY COUNT(f) DESC")
    List<Object[]> findTopFolloweeIds(Pageable pageable);
}
