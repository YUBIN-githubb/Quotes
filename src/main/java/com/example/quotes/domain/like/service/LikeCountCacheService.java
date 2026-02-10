package com.example.quotes.domain.like.service;

import com.example.quotes.domain.like.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LikeCountCacheService {

    private final RedisTemplate<String, String> redisTemplate;
    private final LikeRepository likeRepository;

    private static final String LIKE_COUNT_KEY_PREFIX = "LIKE_COUNT:";

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
}
