package com.example.quotes.domain.comment.service;

import com.example.quotes.common.annotation.Auth;
import com.example.quotes.common.dto.AuthUser;
import com.example.quotes.common.exceptions.CustomException;
import com.example.quotes.domain.comment.entity.Comment;
import com.example.quotes.domain.comment.repository.CommentRepository;
import com.example.quotes.domain.quote.entity.Quote;
import com.example.quotes.domain.quote.service.QuoteQueryService;
import com.example.quotes.domain.user.entity.User;
import com.example.quotes.domain.user.service.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentCommandService {

    private final CommentRepository commentRepository;
    private final UserQueryService userQueryService;
    private final QuoteQueryService quoteQueryService;

    public Comment createComment(Long userId, Long quoteId, String contents) {

        User user = userQueryService.getUserById(userId);
        Quote quote = quoteQueryService.getQuoteById(quoteId);
        return commentRepository.save(Comment.create(user, quote, contents));
    }

    public Comment updateComment(Long userId, Long quoteId, Long commentId, String contents) {

        Comment comment = commentRepository.findWithUserAndQuoteById(commentId)
                .orElseThrow(() -> new CustomException(NOT_FOUND, "해당 댓글이 존재하지 않습니다."));
        comment.updateComment(contents);
        return comment;
    }

    public void deleteComment(Long userId, Long quoteId, Long commentId) {

        Comment comment = commentRepository.findWithUserAndQuoteById(commentId)
                .orElseThrow(() -> new CustomException(NOT_FOUND, "해당 댓글이 존재하지 않습니다."));

        if (!userId.equals(comment.getUser().getId())) {
            throw new CustomException(FORBIDDEN, "해당 댓글의 삭제 권한이 없습니다.");
        }

        comment.updateDeletedAt(LocalDateTime.now());
    }
}
