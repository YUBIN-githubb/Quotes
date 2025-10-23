package com.example.quotes.domain.bookmark.service;

import com.example.quotes.common.exceptions.CustomException;
import com.example.quotes.domain.bookmark.entity.Bookmark;
import com.example.quotes.domain.bookmark.repository.BookmarkRepository;
import com.example.quotes.domain.like.entity.Like;
import com.example.quotes.domain.quote.entity.Quote;
import com.example.quotes.domain.quote.service.QuoteQueryService;
import com.example.quotes.domain.user.entity.User;
import com.example.quotes.domain.user.service.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Book;
import java.time.LocalDateTime;
import java.util.Objects;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional
public class BookmarkCommandService {

    private final BookmarkRepository bookmarkRepository;
    private final UserQueryService userQueryService;
    private final QuoteQueryService quoteQueryService;

    public Bookmark createBookmark(Long userId, Long quoteId) {

        User user = userQueryService.getUserById(userId);
        Quote quote = quoteQueryService.getQuote(quoteId);

        if(bookmarkRepository.existsByUserIdAndQuoteId(user.getId(), quote.getId())) {
            throw new CustomException(BAD_REQUEST, "이미 북마크에 추가했습니다.");
        }

        Bookmark bookmark = Bookmark.create(user, quote);
        return bookmarkRepository.save(bookmark);
    }

    public void deleteBookmark(Long userId, Long quoteId, Long bookmarkId) {

        Bookmark bookmark = bookmarkRepository.findWithUserAndQuoteById(bookmarkId).orElseThrow(
                () -> new CustomException(NOT_FOUND, "해당 북마크를 찾을 수 없습니다."));

        if (!Objects.equals(bookmark.getUser().getId(), userId)) {
            throw new CustomException(BAD_REQUEST, "해당 필사를 북마크한 사용자와 요청한 사용자가 다릅니다.");
        }

        if (!Objects.equals(bookmark.getQuote().getId(), quoteId)) {
            throw new CustomException(BAD_REQUEST, "해당 북마크로 저장된 필사와 요청된 필사가 다릅니다.");
        }

        bookmarkRepository.delete(bookmark);
    }
}
