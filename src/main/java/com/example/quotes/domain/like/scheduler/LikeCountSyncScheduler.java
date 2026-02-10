package com.example.quotes.domain.like.scheduler;

import com.example.quotes.domain.like.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class LikeCountSyncScheduler {

    private final RedisTemplate<String, String> redisTemplate;
    private final LikeRepository likeRepository;

    private static final String LIKE_COUNT_KEY_PREFIX = "LIKE_COUNT:";

    @Scheduled(fixedRate = 300000)
    public void syncLikeCountsToRedis() {
        log.info("좋아요 수 동기화 스케줄러 시작");

        ScanOptions scanOptions = ScanOptions.scanOptions()
                .match(LIKE_COUNT_KEY_PREFIX + "*")
                .count(100)
                .build();

        try (Cursor<String> cursor = redisTemplate.scan(scanOptions)) {
            while (cursor.hasNext()) {
                String key = cursor.next();
                Long quoteId = extractQuoteId(key);

                if (quoteId == null) {
                    continue;
                }

                Long dbCount = likeRepository.countByQuoteId(quoteId);
                String redisValue = redisTemplate.opsForValue().get(key);
                Long redisCount = redisValue != null ? Long.valueOf(redisValue) : 0L;

                if (!dbCount.equals(redisCount)) {
                    log.info("좋아요 수 불일치 감지 - quoteId: {}, Redis: {}, DB: {}", quoteId, redisCount, dbCount);
                    redisTemplate.opsForValue().set(key, String.valueOf(dbCount));
                }

            }
        }

        log.info("좋아요 수 동기화 스케줄러 완료");
    }

    private Long extractQuoteId(String key) {
        try {
            return Long.valueOf(key.replace(LIKE_COUNT_KEY_PREFIX, ""));
        } catch (NumberFormatException e) {
            log.warn("잘못된 LIKE_COUNT 키 형식: {}", key);
            return null;
        }
    }
}
