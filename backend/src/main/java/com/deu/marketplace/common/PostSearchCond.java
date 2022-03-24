package com.deu.marketplace.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PostSearchCond {
    private String title;
    private Long postCategoryId;
}
