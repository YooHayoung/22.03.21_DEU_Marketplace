package com.deu.marketplace.query.dto;

import com.deu.marketplace.domain.deal.entity.DealState;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BuyItemDto {
    private Long itemId;
    private String itemImgFile;
    private String title;
    private Integer price;
    private LocalDateTime lastModifiedDate;
    private Long itemDealId;
    private DealState dealState;
    private Long wishedMemberId;

    @QueryProjection
    public BuyItemDto(Long itemId, String itemImgFile, String title, Integer price,
                      LocalDateTime lastModifiedDate, Long itemDealId,
                      DealState dealState, Long wishedMemberId) {
        this.itemId = itemId;
        this.itemImgFile = itemImgFile;
        this.title = title;
        this.price = price;
        this.lastModifiedDate = lastModifiedDate;
        this.itemDealId = itemDealId;
        this.dealState = dealState;
        this.wishedMemberId = wishedMemberId;
    }
}
