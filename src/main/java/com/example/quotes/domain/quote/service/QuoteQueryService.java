package com.example.quotes.domain.quote.service;

import com.example.quotes.common.dto.AuthUser;
import com.example.quotes.common.exceptions.CustomException;
import com.example.quotes.domain.quote.entity.Quote;
import com.example.quotes.domain.quote.repository.QuoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuoteQueryService {

    private final QuoteRepository quoteRepository;

    public Quote getQuote(Long quoteId) {

        return quoteRepository.findById(quoteId).orElseThrow(
                () -> new CustomException(HttpStatus.NOT_FOUND, "존재하지 않는 필사입니다.")
        );
    }

    public Page<Quote> getQuotes(AuthUser authUser, int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return quoteRepository.findByUserIdAndDeletedAtIsNull(authUser.getUserId(), pageable);
    }

    public Quote getQuoteById(Long quoteId) {

        return quoteRepository.findByIdAndDeletedAtIsNull(quoteId).orElseThrow(
                () -> new CustomException(HttpStatus.NOT_FOUND, "존재하지 않는 필사입니다.")
        );
    }
}
