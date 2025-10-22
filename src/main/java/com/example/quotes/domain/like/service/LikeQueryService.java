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

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeQueryService {

    private final LikeRepository likeRepository;

    public Page<Like> getLikes(Long userId, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        return likeRepository.findByUserId(userId, pageable);
    }
}
