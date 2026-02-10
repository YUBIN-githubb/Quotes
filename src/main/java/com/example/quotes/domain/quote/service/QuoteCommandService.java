package com.example.quotes.domain.quote.service;

import com.example.quotes.common.enums.Category;
import com.example.quotes.common.enums.IsPublic;
import com.example.quotes.common.exceptions.CustomException;
import com.example.quotes.domain.fanout.event.QuoteCreatedEvent;
import com.example.quotes.domain.quote.entity.Quote;
import com.example.quotes.domain.quote.repository.QuoteRepository;
import com.example.quotes.domain.user.entity.User;
import com.example.quotes.domain.user.service.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private final static Duration QUOTE_CACHE_TTL = Duration.ofDays(7);

    public Quote createQuote(Long userId, String title, String author, Category category, Long pageNumber, String sentence, String thought, IsPublic isPublic) {

        User user = userQueryService.getUserById(userId);
        Quote quote = Quote.create(user, title, author, category, pageNumber, sentence, thought, isPublic);
        quoteRepository.save(quote);

        if (quote.getIsPublic() == IsPublic.PUBLIC) {
            String key = "QUOTE_ID:" + quote.getId();

            Map<String, String> quoteRedisMap = convertEntityToMap(quote);
            redisTemplate.opsForHash().putAll(key, quoteRedisMap);
            redisTemplate.expire(key, QUOTE_CACHE_TTL);
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

            Map<String, String> quoteRedisMap = convertEntityToMap(quote);

            redisTemplate.opsForHash().putAll(key, quoteRedisMap);
            redisTemplate.expire(key, QUOTE_CACHE_TTL);
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

    private Map<String, String> convertEntityToMap(Quote quote) {
        Map<String, String> map = new HashMap<>();
        map.put("userId", String.valueOf(quote.getUser().getId()));
        map.put("nickname", quote.getUser().getNickname());
        map.put("title", quote.getTitle());
        map.put("author", quote.getAuthor());
        map.put("category", quote.getCategory().name());
        map.put("pageNumber", String.valueOf(quote.getPageNumber()));
        map.put("sentence", quote.getSentence());
        map.put("thought", quote.getThought());
        map.put("createdAt", String.valueOf(quote.getCreatedAt()));
        map.put("modifiedAt", String.valueOf(quote.getModifiedAt()));
        return map;
    }
}
