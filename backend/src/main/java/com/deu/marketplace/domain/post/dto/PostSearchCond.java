package com.deu.marketplace.domain.post.dto;

import lombok.Getter;

@Getter
public class PostSearchCond {
    private String title;
    private String categoryName;

    public PostSearchCond(String title, String categoryName) {
        this.title = title;
        this.categoryName = categoryName;
    }
}
