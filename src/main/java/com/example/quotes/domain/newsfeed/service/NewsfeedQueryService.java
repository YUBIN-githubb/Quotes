package com.example.quotes.domain.newsfeed.service;

import com.example.quotes.common.enums.Category;
import com.example.quotes.common.enums.IsPublic;
import com.example.quotes.domain.like.entity.Like;
import com.example.quotes.domain.like.repository.LikeRepository;
import com.example.quotes.domain.like.service.LikeCountCacheService;
import com.example.quotes.domain.quote.dto.response.QuoteFeedResponse;
import com.example.quotes.domain.quote.dto.response.QuoteResponse;
import com.example.quotes.domain.quote.entity.Quote;
import com.example.quotes.domain.quote.repository.QuoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class NewsfeedQueryService {

    private final RedisTemplate<String, String> redisTemplate;
    private final QuoteRepository quoteRepository;
    private final LikeRepository likeRepository;
    private final LikeCountCacheService likeCountCacheService;

    private static final String NEWSFEED_KEY_PREFIX = "NEWSFEED:USER:";
    private static final String QUOTE_KEY_PREFIX = "QUOTE_ID:";

    public List<QuoteFeedResponse> getNewsfeed(Long userId, int page, int size) {
        String newsfeedKey = NEWSFEED_KEY_PREFIX + userId;

        // 1. Redis LRANGE로 Quote ID 리스트 조회 (페이지네이션)
        int start = page * size;
        int end = start + size - 1;
        List<String> quoteIdStrings = redisTemplate.opsForList().range(newsfeedKey, start, end);

        if (quoteIdStrings == null || quoteIdStrings.isEmpty()) {
            return Collections.emptyList();
        }

        List<Long> quoteIds = quoteIdStrings.stream()
                .map(Long::valueOf)
                .toList();

        // 2. Redis Hash에서 Quote 데이터 조회
        List<QuoteResponse> quoteResponses = new ArrayList<>();
        List<Long> cacheMissIds = new ArrayList<>();

        for (Long quoteId : quoteIds) {
            String quoteKey = QUOTE_KEY_PREFIX + quoteId;
            Map<Object, Object> quoteData = redisTemplate.opsForHash().entries(quoteKey);

            if (quoteData.isEmpty()) {
                cacheMissIds.add(quoteId);
            } else {
                QuoteResponse response = convertMapToQuoteResponse(quoteId, quoteData);
                quoteResponses.add(response);
            }
        }

        // 3. Cache Miss 시 DB Fallback
        if (!cacheMissIds.isEmpty()) {
            log.info("Cache miss for quote IDs: {}", cacheMissIds);
            List<Quote> quotesFromDb = quoteRepository.findAllById(cacheMissIds);
            for (Quote quote : quotesFromDb) {
                QuoteResponse response = QuoteResponse.of(
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
                        quote.getDeletedAt()
                );
                quoteResponses.add(response);
            }
        }

        // 4. quoteIds 순서대로 정렬 (뉴스피드 순서 유지)
        Map<Long, QuoteResponse> responseMap = quoteResponses.stream()
                .collect(Collectors.toMap(QuoteResponse::getId, Function.identity()));

        List<QuoteResponse> orderedResponses = quoteIds.stream()
                .map(responseMap::get)
                .filter(Objects::nonNull)
                .toList();

        // 5. 좋아요 정보 조회
        List<Long> responseQuoteIds = orderedResponses.stream()
                .map(QuoteResponse::getId)
                .toList();

        List<Like> userLikes = likeRepository.findAllByUserIdAndQuoteIdIn(userId, responseQuoteIds);
        Map<Long, Long> likeMap = userLikes.stream()
                .collect(Collectors.toMap(
                        like -> like.getQuote().getId(),
                        Like::getId
                ));

        // 6. QuoteFeedResponse로 조립
        return orderedResponses.stream()
                .map(quoteResponse -> {
                    Long myLikeId = likeMap.get(quoteResponse.getId());
                    Long likeCount = getLikeCountFromCache(quoteResponse.getId());
                    return QuoteFeedResponse.of(quoteResponse, myLikeId, likeCount);
                })
                .toList();
    }

    public long getNewsfeedSize(Long userId) {
        String newsfeedKey = NEWSFEED_KEY_PREFIX + userId;
        Long size = redisTemplate.opsForList().size(newsfeedKey);
        return size != null ? size : 0L;
    }

    private QuoteResponse convertMapToQuoteResponse(Long quoteId, Map<Object, Object> data) {
        return QuoteResponse.of(
                quoteId,
                Long.valueOf((String) data.get("userId")),
                (String) data.get("nickname"),
                (String) data.get("title"),
                (String) data.get("author"),
                Category.valueOf((String) data.get("category")),
                Long.valueOf((String) data.get("pageNumber")),
                (String) data.get("sentence"),
                (String) data.get("thought"),
                IsPublic.PUBLIC,
                parseLocalDateTime((String) data.get("createdAt")),
                parseLocalDateTime((String) data.get("modifiedAt")),
                null
        );
    }

    private LocalDateTime parseLocalDateTime(String dateTimeStr) {
        if (dateTimeStr == null || dateTimeStr.equals("null")) {
            return null;
        }
        return LocalDateTime.parse(dateTimeStr);
    }

    private Long getLikeCountFromCache(Long quoteId) {
        return likeCountCacheService.getLikeCount(quoteId);
    }
}
