package com.deu.marketplace.web.postCategory.dto;

import com.deu.marketplace.domain.postCategory.entity.PostCategory;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCategoryDto {
    private Long postCategoryId;
    private String postCategoryName;

    @Builder
    public PostCategoryDto(PostCategory postCategory) {
        this.postCategoryId = postCategory.getId();
        this.postCategoryName = postCategory.getCategoryName();
    }

    public PostCategory toEntity() {
        return PostCategory.dtoToEntityBuilder()
                .id(postCategoryId)
                .categoryName(postCategoryName)
                .build();
    }
}
