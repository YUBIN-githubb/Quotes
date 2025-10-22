package com.example.quotes.domain.like.repository;

import com.example.quotes.domain.like.entity.Like;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    boolean existsByUserIdAndQuoteId(Long userId, Long quoteId);

    @EntityGraph(attributePaths = {"user", "quote"})
    Optional<Like> findWithUserAndQuoteById(Long likeId);

    @EntityGraph(attributePaths = {"user", "quote"})
    Page<Like> findByUserId (Long userId, Pageable pageable);
}
