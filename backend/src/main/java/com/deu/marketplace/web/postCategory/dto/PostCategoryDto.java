package com.deu.marketplace.web.postCategory.dto;

import com.deu.marketplace.domain.postCategory.entity.PostCategory;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostCategoryDto {
    private Long id;
    private String categoryName;

    @Builder
    public PostCategoryDto(PostCategory postCategory) {
        this.id = postCategory.getId();
        this.categoryName = postCategory.getCategoryName();
    }
}
