package com.deu.marketplace.web.postImg.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostImgSaveRequestDto {

    private String postImgFile;
    private int postImgSeq;

    public PostImgSaveRequestDto(String postImgFile, int postImgSeq) {
        this.postImgFile = postImgFile;
        this.postImgSeq = postImgSeq;
    }
}
