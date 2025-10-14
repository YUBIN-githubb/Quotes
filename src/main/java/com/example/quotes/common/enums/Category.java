package com.example.quotes.common.enums;

import lombok.Getter;

@Getter
public enum Category {
    // 문학 분야
    NOVEL("소설"),
    POETRY("시"),
    SCENARIO("시나리오"),
    ESSAY("수필"),
    PLAY("희곡"),
    AUTOBIOGRAPHY("자서전"),
    MEMOIR("회고록"),
    CHILDREN_LITERATURE("아동문학"),
    CRITICISM("문학비평"),

    // 인문·사회 분야
    PHILOSOPHY("철학"),
    PSYCHOLOGY("심리학"),
    HISTORY("역사"),
    SOCIOLOGY("사회학"),
    POLITICS("정치"),
    ECONOMY("경제"),
    EDUCATION("교육"),
    LANGUAGE("언어학"),

    // 과학·기술 분야
    SCIENCE("자연과학"),
    MATHEMATICS("수학"),
    COMPUTER("컴퓨터/IT"),
    MEDICINE("의학"),
    ENGINEERING("공학"),

    // 예술·생활 분야
    ART("미술"),
    MUSIC("음악"),
    MOVIE("영화"),
    DESIGN("디자인"),
    COOKING("요리"),
    TRAVEL("여행"),
    SPORTS("스포츠"),

    // 실용·비즈니스 분야
    BUSINESS("비즈니스"),
    MARKETING("마케팅"),
    INVESTMENT("투자"),
    SELF_DEVELOPMENT("자기계발"),

    // 기타
    EDUCATIONAL("교재/참고서"),
    COMIC("만화/그래픽노블"),
    PHOTOBOOK("사진집"),
    ETC("기타");

    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }
}
