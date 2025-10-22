package com.example.quotes.domain.quote.service;

import com.example.quotes.common.dto.AuthUser;
import com.example.quotes.common.enums.Category;
import com.example.quotes.common.enums.IsPublic;
import com.example.quotes.common.exceptions.CustomException;
import com.example.quotes.domain.quote.entity.Quote;
import com.example.quotes.domain.quote.repository.QuoteRepository;
import com.example.quotes.domain.user.entity.User;
import com.example.quotes.domain.user.service.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class QuoteCommandService {

    private final QuoteRepository quoteRepository;
    private final QuoteQueryService quoteQueryService;
    private final UserQueryService userQueryService;

    public Quote createQuote(Long userId, String title, String author, Category category, Long pageNumber, String sentence, String thought, IsPublic isPublic) {

        User user = userQueryService.getUserById(userId);
        Quote quote = Quote.create(user, title, author, category, pageNumber, sentence, thought, isPublic);
        return quoteRepository.save(quote);
    }

    public Quote updateQuote(Long userId, Long quoteId, String title, String author, Category category, Long pageNumber, String sentence, String thought, IsPublic isPublic) {

        Quote quote = quoteQueryService.getQuote(quoteId);

        if (!Objects.equals(userId, quote.getUser().getId())) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "본인 외에 수정은 불가합니다.");
        }

        quote.update(title, author, category, pageNumber, sentence, thought, isPublic);

        return quote;
    }

    public void updateQuoteIsPublic(Long userId, Long quoteId, IsPublic isPublic) {

        Quote quote = quoteQueryService.getQuote(quoteId);

        if (!Objects.equals(userId, quote.getUser().getId())) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "본인 외에 수정은 불가합니다.");
        }

        quote.updateIsPublic(isPublic);
    }

    public void deleteQuote(Long userId, Long quoteId) {

        Quote quote = quoteQueryService.getQuote(quoteId);

        if (!Objects.equals(userId, quote.getUser().getId())) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "본인 외에 삭제는 불가합니다.");
        }

        quote.updateDeletedAt(LocalDateTime.now());
    }
}
