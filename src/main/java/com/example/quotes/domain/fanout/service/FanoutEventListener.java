package com.example.quotes.domain.fanout.service;

import com.example.quotes.domain.fanout.event.QuoteCreatedEvent;
import com.example.quotes.domain.follow.service.FollowQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.Duration;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class FanoutEventListener {

    private final RedisTemplate<String, String> redisTemplate;
    private final FollowQueryService followQueryService;


    @Async("taskExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT) // DB 트랜잭션이 성공적으로 끝난 뒤에 실행
    public void handleQuoteCreated(QuoteCreatedEvent event) {
        Long writerId = event.getUserId();
        Long quoteId = event.getQuoteId();

        log.info("Fan-out 시작 - WriterID: {}, QuoteID: {}", writerId, quoteId);

        // 1. 팔로워 목록 조회 (RDB)
        // (주의: 팔로워가 수십만 명이면 여기서 페이징 처리나 Batch 처리가 필요하지만, 지금은 단순 리스트로)
        List<Long> followerIds = followQueryService.findFollowerIds(writerId);

        log.info("Fan-out 팔로워 조회 - WriterId: {}, FollowerID: {} ", writerId, followerIds);

        followerIds.forEach( followerId -> {
            String key = "NEWSFEED:USER:" + followerId;
            String value = String.valueOf(quoteId);
            redisTemplate.opsForList().leftPush(key, value);
            redisTemplate.opsForList().trim(key, 0, 39);
            redisTemplate.expire(key, Duration.ofDays(7));
        });

        log.info("Fan-out 완료 - {}명의 팔로워에게 배달됨.", followerIds.size());
    }

}
