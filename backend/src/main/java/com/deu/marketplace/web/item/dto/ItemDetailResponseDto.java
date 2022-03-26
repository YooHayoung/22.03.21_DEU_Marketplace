package com.deu.marketplace.web.item.dto;

import com.deu.marketplace.domain.deal.entity.Deal;
import com.deu.marketplace.domain.deal.entity.DealState;
import com.deu.marketplace.domain.item.entity.BookState;
import com.deu.marketplace.domain.item.entity.Classification;
import com.deu.marketplace.domain.item.entity.Item;
import com.deu.marketplace.domain.itemImg.entity.ItemImg;
import com.deu.marketplace.web.itemImg.dto.ItemImgResponseDto;
import com.deu.marketplace.web.lecture.dto.LectureDto;
import com.querydsl.core.Tuple;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
public class ItemDetailResponseDto {
    private ItemDetailDto itemDetailDto;
    private ItemWishInfo wishInfo;
    private DealState dealState;
    private Long chatRoomId;
    private List<ItemImgResponseDto> imgList;

    @Builder
    public ItemDetailResponseDto(Item item, Optional<Tuple> wishInfo,
                                 Optional<Deal> deal, Long chatRoomId, List<ItemImg> itemImgs) {
        this.itemDetailDto = ItemDetailDto.builder().item(item).build();
        if (wishInfo.isPresent()) {
            Tuple tuple = wishInfo.orElseThrow();
            this.wishInfo = ItemWishInfo.builder()
                    .wishCount(tuple.get(1, Long.class))
                    .myWish(tuple.get(2, Boolean.class))
                    .build();
        }
        if (deal.isPresent()) {
            this.dealState = deal.orElseThrow().getDealState();
        }
        this.chatRoomId = chatRoomId;
        this.imgList = itemImgs.stream().map(ItemImgResponseDto::new).collect(Collectors.toList());
    }
}
