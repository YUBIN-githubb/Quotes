package com.example.quotes.domain.bookmark.controller;

import com.example.quotes.common.annotation.Auth;
import com.example.quotes.common.dto.AuthUser;
import com.example.quotes.domain.bookmark.dto.response.BookmarkResponse;
import com.example.quotes.domain.bookmark.dto.response.PageBookmarkResponse;
import com.example.quotes.domain.bookmark.entity.Bookmark;
import com.example.quotes.domain.bookmark.service.BookmarkCommandService;
import com.example.quotes.domain.bookmark.service.BookmarkQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkCommandService bookmarkCommandService;
    private final BookmarkQueryService bookmarkQueryService;

    @PostMapping("/quotes/{quoteId}/bookmarks")
    public ResponseEntity<BookmarkResponse> createBookmark(
            @Auth AuthUser authUser,
            @PathVariable Long quoteId) {

        Bookmark bookmark = bookmarkCommandService.createBookmark(authUser.getUserId(), quoteId);
        BookmarkResponse bookmarkResponse = BookmarkResponse.of(bookmark.getId(), bookmark.getQuote().getId(), bookmark.getQuote().getTitle(), bookmark.getQuote().getCategory());
        return ResponseEntity.ok(bookmarkResponse);
    }

    @DeleteMapping ("/quotes/{quoteId}/bookmarks/{bookmarkId}")
    public ResponseEntity<Void> deleteBookmark(
            @Auth AuthUser authUser,
            @PathVariable Long quoteId,
            @PathVariable Long bookmarkId) {

        bookmarkCommandService.deleteBookmark(authUser.getUserId(), quoteId, bookmarkId);
        return ResponseEntity.ok().build();
    }

    @GetMapping ("/bookmarks")
    public ResponseEntity<PageBookmarkResponse> getBookmarks(
            @Auth AuthUser authUser,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Bookmark> bookmarks = bookmarkQueryService.getBookmarks(authUser.getUserId(), pageable);
        PageBookmarkResponse pageBookmarkResponse = PageBookmarkResponse.of(bookmarks.getContent(), bookmarks.getSize(), bookmarks.getNumber(), bookmarks.getTotalElements(), bookmarks.getTotalPages());
        return ResponseEntity.ok(pageBookmarkResponse);
    }
}
