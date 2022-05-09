package com.deu.marketplace.query.postListView.dto;

import com.deu.marketplace.domain.postImg.entity.PostImg;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostImgDto {
    private Long ImgId;
    private String img;
    private int seq;

    @Builder
    public PostImgDto(PostImg postImg) {
        this.ImgId = postImg.getId();
        this.img = postImg.getImgFile();
        this.seq = postImg.getImgSeq();
    }

    public void imgToImgUrl(String imgUrl) {
        this.img = imgUrl;
    }
}
