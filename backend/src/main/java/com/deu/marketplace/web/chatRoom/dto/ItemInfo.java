package com.deu.marketplace.web.chatRoom.dto;

import com.deu.marketplace.domain.deal.entity.DealState;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ItemInfo {
    private Long itemId;
    private String itemImg;
    private String title;
    private DealState dealState;

    @Builder
    public ItemInfo(Long itemId, String itemImg, String title, DealState dealState) {
        this.itemId = itemId;
        this.itemImg = itemImg;
        this.title = title;
        this.dealState = dealState;
    }
}
