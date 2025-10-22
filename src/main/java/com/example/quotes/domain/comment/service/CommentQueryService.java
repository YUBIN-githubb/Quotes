package com.example.quotes.domain.comment.service;

import com.example.quotes.domain.comment.entity.Comment;
import com.example.quotes.domain.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentQueryService {

    private final CommentRepository commentRepository;

    public Page<Comment> getComments(Long quoteId, Pageable pageable) {

        return commentRepository.findByQuoteIdAndDeletedAtIsNull(quoteId, pageable);
    }
}
