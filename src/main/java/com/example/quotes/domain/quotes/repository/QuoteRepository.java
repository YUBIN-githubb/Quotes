package com.example.quotes.domain.quotes.repository;

import com.example.quotes.domain.quotes.entity.Quote;
import com.example.quotes.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuoteRepository extends JpaRepository<Quote, Long> {

    @EntityGraph(attributePaths = {"user"})
    Page<Quote> findByUserIdAndDeletedAtIsNull(Long userId, Pageable pageable);

    @EntityGraph(attributePaths = {"user"})
    Optional<Quote> findByIdAndDeletedAtIsNull(Long id);
}
