package com.deu.marketplace.web.postComment.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCommentSaveDto {
    private Long postId;
    private String comment;

    @Builder
    public PostCommentSaveDto(Long postId, String comment) {
        this.postId = postId;
        this.comment = comment;
    }
}
