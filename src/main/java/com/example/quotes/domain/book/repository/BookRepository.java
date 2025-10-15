package com.example.quotes.domain.book.repository;

import com.example.quotes.domain.book.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    Page<Book> findByDeletedAtIsNull(Pageable pageable);
}
