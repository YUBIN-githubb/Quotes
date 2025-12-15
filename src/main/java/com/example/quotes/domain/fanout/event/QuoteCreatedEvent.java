package com.example.quotes.domain.fanout.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QuoteCreatedEvent {
    private final Long quoteId;
    private final Long userId;
}
