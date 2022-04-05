package com.deu.marketplace.query.postListView.dto;

import com.deu.marketplace.domain.post.entity.Post;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostDetailViewDto {
    private PostInfo postInfo;
    private int postRecommendCount;
    private boolean myRecommend;
    private List<PostImgDto> postImgs = new ArrayList<>();

//    @Builder
//    public PostDetailViewDto(PostInfo postInfo, int postRecommendCount,
//                             boolean myRecommend, List<PostImgDto> postImgs) {
//        this.postInfo = postInfo;
//        this.postRecommendCount = postRecommendCount;
//        this.myRecommend = myRecommend;
//        this.postImgs = postImgs;
//    }

    @QueryProjection
    public PostDetailViewDto(Post post, int postRecommendCount,
                             boolean myRecommend) {
        this.postInfo = PostInfo.builder()
                .post(post)
                .build();
        this.postRecommendCount = postRecommendCount;
        this.myRecommend = myRecommend;
        this.postImgs = post.getPostImgs().stream().map(PostImgDto::new).collect(Collectors.toList());
    }
}
