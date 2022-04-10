package com.deu.marketplace.query.postListView.dto;

import com.deu.marketplace.domain.post.entity.Post;
import com.deu.marketplace.web.item.dto.MemberShortInfoDto;
import com.deu.marketplace.web.postCategory.dto.PostCategoryDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostInfo {
    private Long postId;
    private PostCategoryDto postCategoryInfo;
    private String title;
    private String content;
    private MemberShortInfoDto memberShortInfo;
    private String createdDate;

//    @Builder
//    public PostInfo(Long postId, String postCategoryName, String title, String content,
//                    MemberShortInfoDto memberShortInfo, String createdDate) {
//        this.postId = postId;
//        this.postCategoryName = postCategoryName;
//        this.title = title;
//        this.content = content;
//        this.memberShortInfo = memberShortInfo;
//        this.createdDate = createdDate;
//    }

    @Builder
    public PostInfo(Post post) {
        this.postId = post.getId();
        this.postCategoryInfo = PostCategoryDto.builder().postCategory(post.getPostCategory()).build();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.memberShortInfo = MemberShortInfoDto.builder().member(post.getWriter()).build();
        this.createdDate = post.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
