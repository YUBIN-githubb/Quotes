package com.example.quotes.domain.comment.repository;

import com.example.quotes.domain.comment.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @EntityGraph(attributePaths = {"user", "quote"})
    Optional<Comment> findWithUserAndQuoteById(Long commentId);

    Page<Comment> findByQuoteIdAndDeletedAtIsNull(Long quoteId, Pageable pageable);
}
