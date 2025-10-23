package com.example.quotes.domain.bookmark.service;

import com.example.quotes.domain.bookmark.entity.Bookmark;
import com.example.quotes.domain.bookmark.repository.BookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookmarkQueryService {

    private final BookmarkRepository bookmarkRepository;

    public Page<Bookmark> getBookmarks(Long userId, Pageable pageable) {

        return bookmarkRepository.findByUserId(userId, pageable);
    }
}
