package com.example.quotes.common.enums;

import lombok.Getter;

@Getter
public enum Visibility {
    PUBLIC("공개"),
    PRIVATE("비공개");

    private final String displayName;

    Visibility(String displayName) {
        this.displayName = displayName;
    }
}
