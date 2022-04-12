package com.deu.marketplace.web.post.dto;

import com.deu.marketplace.web.postCategory.dto.PostCategoryDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostSaveInfoDto {

    private PostCategoryDto postCategoryInfo;
    private String title;
    private String content;

    @Builder
    public PostSaveInfoDto(PostCategoryDto postCategoryInfo, String title, String content) {
        this.postCategoryInfo = postCategoryInfo;
        this.title = title;
        this.content = content;
    }
}
