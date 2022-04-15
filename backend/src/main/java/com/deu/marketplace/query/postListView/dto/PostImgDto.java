package com.deu.marketplace.query.postListView.dto;

import com.deu.marketplace.domain.postImg.entity.PostImg;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostImgDto {
    private Long postImgId;
    private String imgFile;
    private int seq;

    @Builder
    public PostImgDto(PostImg postImg) {
        this.postImgId = postImg.getId();
        this.imgFile = postImg.getImgFile();
        this.seq = postImg.getImgSeq();
    }

    public void imgToImgUrl(String imgUrl) {
        this.imgFile = imgUrl;
    }
}
