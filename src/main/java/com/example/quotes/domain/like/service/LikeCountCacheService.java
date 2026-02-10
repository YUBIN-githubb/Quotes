package com.example.quotes.domain.like.service;

import com.example.quotes.domain.like.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LikeCountCacheService {

    private final RedisTemplate<String, String> redisTemplate;
    private final LikeRepository likeRepository;

    private static final String LIKE_COUNT_KEY_PREFIX = "LIKE_COUNT:";
    private static final String QUOTE_LIKED_KEY_PREFIX = "QUOTE_LIKED:";

    public Long getLikeCount(Long quoteId) {
        String key = LIKE_COUNT_KEY_PREFIX + quoteId;
        String value = redisTemplate.opsForValue().get(key);

        if (value != null) {
            return Long.valueOf(value);
        }

        Long countFromDb = likeRepository.countByQuoteId(quoteId);
        redisTemplate.opsForValue().set(key, String.valueOf(countFromDb));
        return countFromDb;
    }

    public void increment(Long quoteId) {
        String key = LIKE_COUNT_KEY_PREFIX + quoteId;

        // 키가 없으면 DB에서 조회 후 세팅
        if (Boolean.FALSE.equals(redisTemplate.hasKey(key))) {
            Long countFromDb = likeRepository.countByQuoteId(quoteId);
            redisTemplate.opsForValue().set(key, String.valueOf(countFromDb));
        }

        redisTemplate.opsForValue().increment(key);
    }

    public void decrement(Long quoteId) {
        String key = LIKE_COUNT_KEY_PREFIX + quoteId;

        // 키가 없으면 DB에서 조회 후 세팅
        if (Boolean.FALSE.equals(redisTemplate.hasKey(key))) {
            Long countFromDb = likeRepository.countByQuoteId(quoteId);
            redisTemplate.opsForValue().set(key, String.valueOf(countFromDb));
        }

        Long currentValue = redisTemplate.opsForValue().decrement(key);
        if (currentValue != null && currentValue < 0) {
            redisTemplate.opsForValue().set(key, "0");
        }
    }

    public boolean isLiked(Long userId, Long quoteId) {
        String key = QUOTE_LIKED_KEY_PREFIX + quoteId;

        if (Boolean.FALSE.equals(redisTemplate.hasKey(key))) {
            // DB fallback: Set 초기화
            List<Long> userIds = likeRepository.findAllUserIdsByQuoteId(quoteId);
            if (userIds.isEmpty()) {
                return false;
            }
            String[] userIdStrings = userIds.stream()
                    .map(String::valueOf)
                    .toArray(String[]::new);
            redisTemplate.opsForSet().add(key, userIdStrings);
        }

        return Boolean.TRUE.equals(
                redisTemplate.opsForSet().isMember(key, String.valueOf(userId))
        );
    }

    public void addLike(Long userId, Long quoteId) {
        String key = QUOTE_LIKED_KEY_PREFIX + quoteId;
        redisTemplate.opsForSet().add(key, String.valueOf(userId));
    }

    public void removeLike(Long userId, Long quoteId) {
        String key = QUOTE_LIKED_KEY_PREFIX + quoteId;
        redisTemplate.opsForSet().remove(key, String.valueOf(userId));
    }
}
