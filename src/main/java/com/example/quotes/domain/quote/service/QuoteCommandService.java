package com.example.quotes.domain.quote.service;

import com.example.quotes.common.dto.AuthUser;
import com.example.quotes.common.enums.Category;
import com.example.quotes.common.enums.IsPublic;
import com.example.quotes.common.exceptions.CustomException;
import com.example.quotes.domain.fanout.event.QuoteCreatedEvent;
import com.example.quotes.domain.quote.entity.Quote;
import com.example.quotes.domain.quote.repository.QuoteRepository;
import com.example.quotes.domain.user.entity.User;
import com.example.quotes.domain.user.service.UserQueryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class QuoteCommandService {

    private final QuoteRepository quoteRepository;
    private final QuoteQueryService quoteQueryService;
    private final UserQueryService userQueryService;
    private final RedisTemplate<String, String> redisTemplate;
    private final ApplicationEventPublisher eventPublisher;

    public Quote createQuote(Long userId, String title, String author, Category category, Long pageNumber, String sentence, String thought, IsPublic isPublic) {

        User user = userQueryService.getUserById(userId);
        Quote quote = Quote.create(user, title, author, category, pageNumber, sentence, thought, isPublic);
        quoteRepository.save(quote);

        if (quote.getIsPublic() == IsPublic.PUBLIC) {
            String key = "QUOTE_ID:" + quote.getId();

            Map<String, String> quoteMap = new HashMap<>();
            quoteMap.put("id", String.valueOf(quote.getId()));
            quoteMap.put("userId", String.valueOf(quote.getUser().getId()));
            quoteMap.put("title", quote.getTitle());
            quoteMap.put("author", quote.getAuthor());
            quoteMap.put("category", quote.getCategory().name()); // Enum -> String
            quoteMap.put("pageNumber", String.valueOf(quote.getPageNumber()));
            quoteMap.put("sentence", quote.getSentence());
            quoteMap.put("thought", quote.getThought());
            quoteMap.put("isPublic", quote.getIsPublic().name()); // Enum -> String
            // 날짜는 ISO-8601 형식 문자열로 변환 (예: 2024-12-13T14:30:00)
            quoteMap.put("createdAt", quote.getCreatedAt().toString());
            quoteMap.put("modifiedAt", quote.getModifiedAt().toString());

            redisTemplate.opsForHash().putAll(key, quoteMap);
            redisTemplate.expire(key, Duration.ofDays(7));

            eventPublisher.publishEvent(new QuoteCreatedEvent(quote.getId(), userId));
        }
        return quote;
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

        if (isPublic == IsPublic.PUBLIC) {
            String key = "QUOTE_ID:" + quote.getId();

            Map<String, String> quoteMap = new HashMap<>();
            quoteMap.put("id", String.valueOf(quote.getId()));
            quoteMap.put("userId", String.valueOf(quote.getUser().getId()));
            quoteMap.put("title", quote.getTitle());
            quoteMap.put("author", quote.getAuthor());
            quoteMap.put("category", quote.getCategory().name()); // Enum -> String
            quoteMap.put("pageNumber", String.valueOf(quote.getPageNumber()));
            quoteMap.put("sentence", quote.getSentence());
            quoteMap.put("thought", quote.getThought());
            quoteMap.put("isPublic", quote.getIsPublic().name()); // Enum -> String
            // 날짜는 ISO-8601 형식 문자열로 변환 (예: 2024-12-13T14:30:00)
            quoteMap.put("createdAt", quote.getCreatedAt().toString());
            quoteMap.put("modifiedAt", quote.getModifiedAt().toString());

            redisTemplate.opsForHash().putAll(key, quoteMap);
            redisTemplate.expire(key, Duration.ofDays(7));
        } else {
            String key = "QUOTE_ID:" + quote.getId();
            redisTemplate.delete(key);
        }
    }

    public void deleteQuote(Long userId, Long quoteId) {

        Quote quote = quoteQueryService.getQuote(quoteId);

        if (!Objects.equals(userId, quote.getUser().getId())) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "본인 외에 삭제는 불가합니다.");
        }

        quote.updateDeletedAt(LocalDateTime.now());

        String key = "QUOTE_ID:" + quoteId;
        redisTemplate.delete(key);
    }
}
