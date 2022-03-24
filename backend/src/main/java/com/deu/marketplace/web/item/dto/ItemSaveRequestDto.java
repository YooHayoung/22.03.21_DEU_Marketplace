package com.deu.marketplace.web.item.dto;

import com.deu.marketplace.domain.item.entity.Classification;
import com.deu.marketplace.domain.item.entity.Item;
import com.deu.marketplace.domain.member.entity.Member;
import com.deu.marketplace.web.itemImg.dto.ItemImgRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemSaveRequestDto {
    private Long memberId; //
    private String classification;
    private List<ItemImgRequestDto> itemImgs;
    private String title;
    private Long itemCategoryId;
    private Long lectureId;
    private String writeState;
    private String surfaceState;
    private int regularPrice;
    private int price;
    private String description;
}
