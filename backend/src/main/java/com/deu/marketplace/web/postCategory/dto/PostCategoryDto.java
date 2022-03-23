package com.deu.marketplace.web.postCategory.dto;

import com.deu.marketplace.domain.postCategory.entity.PostCategory;
import lombok.Getter;

@Getter
public class PostCategoryDto {
    private Long id;
    private String categoryName;

    public PostCategoryDto(PostCategory postCategory) {
        this.id = postCategory.getId();
        this.categoryName = postCategory.getCategoryName();
    }
}
