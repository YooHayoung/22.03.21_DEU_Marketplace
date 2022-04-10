package com.deu.marketplace.web.postComment.dto;

import com.deu.marketplace.domain.postComment.entity.PostComment;
import com.deu.marketplace.web.item.dto.MemberShortInfoDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCommentDto {

    private Long postCommentId;
    private MemberShortInfoDto memberInfo;
    private String content;
    private String createdDate;

    public PostCommentDto(PostComment postComment) {
        this.postCommentId = postComment.getId();
        this.memberInfo = MemberShortInfoDto.builder().member(postComment.getWriter()).build();
        this.content = postComment.getContent();
        this.createdDate = postComment.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
