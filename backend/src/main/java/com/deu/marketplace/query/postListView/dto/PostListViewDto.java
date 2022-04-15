package com.deu.marketplace.query.postListView.dto;

import com.deu.marketplace.domain.post.entity.Post;
import com.deu.marketplace.domain.postImg.entity.PostImg;
import com.deu.marketplace.web.postCategory.dto.PostCategoryDto;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostListViewDto {
    private Long postId;
    private String postCategoryName;
    private String title;
    private String content;
    private String memberNickname;
    private String createdDate;
    private String firstImg;
    private int recommendCount;
    private int commentCount;
//    private int viewCount;


//    @Builder
//    public PostListViewDto(Long postId, String postCategoryName, String title,
//                           String content, String memberNickname, String createdDate,
//                           String firstImg, int recommendCount, int commentCount) {
//        this.postId = postId;
//        this.postCategoryName = postCategoryName;
//        this.title = title;
//        this.content = content;
//        this.memberNickname = memberNickname;
//        this.createdDate = createdDate;
//        this.firstImg = firstImg;
//        this.recommendCount = recommendCount;
//        this.commentCount = commentCount;
//    }

    @Builder
    public PostListViewDto(Post post, PostImg postImg, int recommendCount, int commentCount) {
        this.postId = post.getId();
        this.postCategoryName = post.getPostCategory().getCategoryName();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.memberNickname = post.getWriter().getNickname();
        this.createdDate = post.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        if (postImg != null)
            this.firstImg = postImg.getImgFile();
        this.recommendCount = recommendCount;
        this.commentCount = commentCount;
    }

    public boolean isImgExist() {
        if (this.firstImg == null)
            return false;
        else return true;
    }

    public void imgFileToImgUrl(String imgUrl) {
        this.firstImg = imgUrl;
    }
}
