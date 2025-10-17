package com.example.quotes.domain.book.dto.response;

import lombok.Getter;

@Getter
public class KakaoBookMeta {
    private Boolean is_end;
    private Integer pageable_count;
    private Integer total_count;
}
