package com.example.quotes.domain.like.service;

import com.example.quotes.common.dto.AuthUser;
import com.example.quotes.domain.like.entity.Like;
import com.example.quotes.domain.like.repository.LikeRepository;
import com.example.quotes.domain.quote.entity.Quote;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeQueryService {

    private final LikeRepository likeRepository;

    public Page<Like> getLikes(Long userId, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        return likeRepository.findByUserId(userId, pageable);
    }

    public Map<Long, Long> getMyLikeMap(Long userId, List<Quote> quotes) {
        if (userId == null || quotes.isEmpty()) {
            return Collections.emptyMap();
        }

        List<Long> quoteIds = quotes.stream()
                .map(Quote::getId)
                .toList();

        List<Like> myLikes = likeRepository.findAllByUserIdAndQuoteIdIn(userId, quoteIds);

        // QuoteId -> LikeId 매핑으로 변환
        return myLikes.stream()
                .collect(Collectors.toMap(
                        like -> like.getQuote().getId(),
                        Like::getId
                ));
    }
}
