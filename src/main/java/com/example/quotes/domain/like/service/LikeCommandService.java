package com.example.quotes.domain.like.service;

import com.example.quotes.common.exceptions.CustomException;
import com.example.quotes.domain.like.entity.Like;
import com.example.quotes.domain.like.repository.LikeRepository;
import com.example.quotes.domain.quote.entity.Quote;
import com.example.quotes.domain.quote.service.QuoteQueryService;
import com.example.quotes.domain.user.entity.User;
import com.example.quotes.domain.user.service.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeCommandService {

    private final LikeRepository likeRepository;
    private final UserQueryService userQueryService;
    private final QuoteQueryService quoteQueryService;

    public void createLike(Long userId, Long quoteId) {

        User user = userQueryService.getUserById(userId);
        Quote quote = quoteQueryService.getQuote(quoteId);

        if(likeRepository.existsByUserIdAndQuoteId(user.getId(), quote.getId())) {
            throw new CustomException(BAD_REQUEST, "이미 해당 좋아요를 눌렀습니다.");
        }

        Like like = Like.create(user, quote);
        likeRepository.save(like);
    }

    public void deleteLike(Long userId, Long quoteId, Long likeId) {

        Like like = likeRepository.findWithUserAndQuoteById(likeId).orElseThrow(
                () -> new CustomException(NOT_FOUND, "해당 좋아요를 찾을 수 없습니다."));

        if (!Objects.equals(like.getUser().getId(), userId)) {
            throw new CustomException(BAD_REQUEST, "해당 좋아요를 누른 사용자와 요청한 사용자가 다릅니다.");
        }

        if (!Objects.equals(like.getQuote().getId(), quoteId)) {
            throw new CustomException(BAD_REQUEST, "해당 좋아요가 눌린 필사와 요청된 필사가 다릅니다.");
        }

        likeRepository.delete(like);
    }
}
