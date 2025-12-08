package com.example.quotes.domain.quote.repository;

import com.example.quotes.common.enums.IsPublic;
import com.example.quotes.domain.quote.entity.Quote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface QuoteRepository extends JpaRepository<Quote, Long> {

    @EntityGraph(attributePaths = {"user"})
    Page<Quote> findByUserIdAndDeletedAtIsNull(Long userId, Pageable pageable);

    @EntityGraph(attributePaths = {"user"})
    Optional<Quote> findByIdAndDeletedAtIsNull(Long id);

    @EntityGraph(attributePaths = {"user"})
    Page<Quote> findByDeletedAtIsNullAndIsPublic(Pageable pageable, IsPublic isPublic);

    @EntityGraph(attributePaths = {"user"})
    Page<Quote> findByUserIdAndDeletedAtIsNullAndIsPublic(Pageable pageable, Long userId, IsPublic isPublic);
}
