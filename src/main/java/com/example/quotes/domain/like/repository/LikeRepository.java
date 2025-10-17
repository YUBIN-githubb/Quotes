package com.example.quotes.domain.like.repository;

import com.example.quotes.domain.like.entity.Like;
import com.example.quotes.domain.quote.entity.Quote;
import com.example.quotes.domain.user.entity.User;
import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    boolean existsByUserIdAndQuoteId(Long userId, Long quoteId);

    @EntityGraph(attributePaths = {"user", "quote"})
    Optional<Like> findWithUserAndShowById(Long likeId);
}
