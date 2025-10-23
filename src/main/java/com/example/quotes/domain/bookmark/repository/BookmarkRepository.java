package com.example.quotes.domain.bookmark.repository;

import com.example.quotes.domain.bookmark.entity.Bookmark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    boolean existsByUserIdAndQuoteId(Long userId, Long quoteId);

    @EntityGraph(attributePaths = {"user", "quote"})
    Optional<Bookmark> findWithUserAndQuoteById(Long bookmarkId);

    @EntityGraph(attributePaths = {"user", "quote"})
    Page<Bookmark> findByUserId(Long userId, Pageable pageable);
}
