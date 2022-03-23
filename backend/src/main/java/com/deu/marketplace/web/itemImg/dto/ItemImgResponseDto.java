package com.deu.marketplace.web.itemImg.dto;

import com.deu.marketplace.domain.itemImg.entity.ItemImg;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ItemImgResponseDto {
    private Long imgId;
    private Long itemId;
    private String img;
    private int seq;

    public ItemImgResponseDto(ItemImg itemImg) {
        this.imgId = itemImg.getId();
        this.itemId = itemImg.getItem().getId();
        this.img = itemImg.getImgFile();
        this.seq = itemImg.getImgSeq();
    }
}
