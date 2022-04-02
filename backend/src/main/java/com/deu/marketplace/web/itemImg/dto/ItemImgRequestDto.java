package com.deu.marketplace.web.itemImg.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemImgRequestDto {
    private String imgFile;
    private int imgSeq;

//    public ItemImg toEntity() {
//        return ItemImg.builder()
//                .imgFile(imgFile)
//                .imgSeq(imgSeq)
//                .build();
//    }
}
