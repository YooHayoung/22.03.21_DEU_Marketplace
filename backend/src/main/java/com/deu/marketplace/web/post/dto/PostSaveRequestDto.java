package com.deu.marketplace.web.post.dto;

import com.deu.marketplace.domain.member.entity.Member;
import com.deu.marketplace.domain.post.entity.Post;
import com.deu.marketplace.domain.postImg.entity.PostImg;
import com.deu.marketplace.query.postListView.dto.PostImgDto;
import com.deu.marketplace.web.postCategory.dto.PostCategoryDto;
import com.deu.marketplace.web.postImg.dto.PostImgSaveRequestDto;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PostSaveRequestDto {
    private PostCategoryDto postCategoryInfo;
    private String title;
    private String content;

    public Post toEntity(Member member) {
        Post post = Post.builder()
                .postCategory(postCategoryInfo.toEntity())
                .writer(member)
                .title(title)
                .content(content)
                .build();
        return post;
    }

//    public Post toEntity() {
//        Post post = Post.builder()
//                .postCategory(postInfo.getPostCategoryInfo().toEntity())
//                .title(postInfo.getTitle())
//                .content(postInfo.getContent())
//                .build();
//        List<PostImg> postImgList = postImgs.stream().map(postImg -> PostImg.builder()
//                .post(post)
//                .imgFile(postImg.getPostImgFile())
//                .imgSeq(postImg.getPostImgSeq())
//                .build()).collect(Collectors.toList());
//        return post;
//    }
}
