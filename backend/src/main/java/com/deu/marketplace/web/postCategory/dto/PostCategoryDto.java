package com.deu.marketplace.web.postCategory.dto;

import com.deu.marketplace.domain.postCategory.entity.PostCategory;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCategoryDto {
    private Long categoryId;
    private String categoryName;

    @Builder
    public PostCategoryDto(PostCategory postCategory) {
        this.categoryId = postCategory.getId();
        this.categoryName = postCategory.getCategoryName();
    }

    public PostCategory toEntity() {
        return PostCategory.dtoToEntityBuilder()
                .id(categoryId)
                .categoryName(categoryName)
                .build();
    }
}
