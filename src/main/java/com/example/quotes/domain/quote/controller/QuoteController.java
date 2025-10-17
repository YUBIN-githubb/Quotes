package com.example.quotes.domain.quote.controller;

import com.example.quotes.common.annotation.Auth;
import com.example.quotes.common.dto.AuthUser;
import com.example.quotes.domain.quote.dto.request.CreateQuoteRequest;
import com.example.quotes.domain.quote.dto.request.UpdateQuoteIsPublicRequest;
import com.example.quotes.domain.quote.dto.request.UpdateQuoteRequest;
import com.example.quotes.domain.quote.dto.response.PageQuoteResponse;
import com.example.quotes.domain.quote.dto.response.QuoteResponse;
import com.example.quotes.domain.quote.entity.Quote;
import com.example.quotes.domain.quote.service.QuoteCommandService;
import com.example.quotes.domain.quote.service.QuoteQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class QuoteController {

    private final QuoteCommandService quoteCommandService;
    private final QuoteQueryService quoteQueryService;

    @PostMapping("/quotes")
    public ResponseEntity<QuoteResponse> createQuote(
            @Auth AuthUser authUser,
            @Valid @RequestBody CreateQuoteRequest request) {

        Quote quote = quoteCommandService.createQuote(
                authUser,
                request.getTitle(),
                request.getAuthor(),
                request.getCategory(),
                request.getPageNumber(),
                request.getSentence(),
                request.getThought(),
                request.getIsPublic()
        );

        QuoteResponse quoteResponse = QuoteResponse.of(
                quote.getId(),
                quote.getUser().getId(),
                quote.getUser().getNickname(),
                quote.getTitle(),
                quote.getAuthor(),
                quote.getCategory(),
                quote.getPageNumber(),
                quote.getSentence(),
                quote.getThought(),
                quote.getIsPublic(),
                quote.getCreatedAt(),
                quote.getModifiedAt(),
                quote.getDeletedAt());
        return ResponseEntity.ok(quoteResponse);
    }

    @PutMapping("/quotes/{quoteId}")
    public ResponseEntity<QuoteResponse> updateQuote(
            @Auth AuthUser authUser,
            @PathVariable Long quoteId,
            @Valid @RequestBody UpdateQuoteRequest request) {

        Quote quote = quoteCommandService.updateQuote(
                authUser,
                quoteId,
                request.getTitle(),
                request.getAuthor(),
                request.getCategory(),
                request.getPageNumber(),
                request.getSentence(),
                request.getThought(),
                request.getIsPublic()
        );

        QuoteResponse quoteResponse = QuoteResponse.of(
                quote.getId(),
                quote.getUser().getId(),
                quote.getUser().getNickname(),
                quote.getTitle(),
                quote.getAuthor(),
                quote.getCategory(),
                quote.getPageNumber(),
                quote.getSentence(),
                quote.getThought(),
                quote.getIsPublic(),
                quote.getCreatedAt(),
                quote.getModifiedAt(),
                quote.getDeletedAt());
        return ResponseEntity.ok(quoteResponse);
    }

    @PatchMapping("/quotes/{quoteId}")
    public ResponseEntity<String> updateQuoteIsPublic(
            @Auth AuthUser authUser,
            @PathVariable Long quoteId,
            @Valid @RequestBody UpdateQuoteIsPublicRequest request) {

        quoteCommandService.updateQuoteIsPublic(authUser, quoteId, request.getIsPublic());
        return ResponseEntity.ok("공개여부가 성공적으로 전환되었습니다.");
    }

    @DeleteMapping("/quotes/{quoteId}")
    public ResponseEntity<String> deleteQuote(
            @Auth AuthUser authUser,
            @PathVariable Long quoteId) {

        quoteCommandService.deleteQuote(authUser, quoteId);
        return ResponseEntity.ok("성공적으로 삭제되었습니다.");
    }

    @GetMapping("/quotes")
    public ResponseEntity<PageQuoteResponse> getQuotes(
            @Auth AuthUser authUser,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<Quote> quotes = quoteQueryService.getQuotes(authUser, page, size);
        PageQuoteResponse pageQuoteResponse = PageQuoteResponse.of(quotes.getContent(), quotes.getSize(), quotes.getNumber(), quotes.getTotalElements(), quotes.getTotalPages());
        return ResponseEntity.ok(pageQuoteResponse);
    }

    @GetMapping("/quotes/{quoteId}")
    public ResponseEntity<QuoteResponse> getQuote(
            @PathVariable Long quoteId) {

        Quote quote = quoteQueryService.getQuoteById(quoteId);
        QuoteResponse quoteResponse = QuoteResponse.of(
                quote.getId(),
                quote.getUser().getId(),
                quote.getUser().getNickname(),
                quote.getTitle(),
                quote.getAuthor(),
                quote.getCategory(),
                quote.getPageNumber(),
                quote.getSentence(),
                quote.getThought(),
                quote.getIsPublic(),
                quote.getCreatedAt(),
                quote.getModifiedAt(),
                quote.getDeletedAt());
        return ResponseEntity.ok(quoteResponse);
    }

}
