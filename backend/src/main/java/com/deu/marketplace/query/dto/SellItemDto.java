package com.deu.marketplace.query.dto;

import com.deu.marketplace.domain.deal.entity.DealState;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SellItemDto {
    private Long itemId;
    private String itemImgFile;
    private String title;
    private Integer price;
    private LocalDateTime lastModifiedDate;
    private Long itemCategoryId;
    private String itemCategoryName;
    private String lectureName;
    private String professorName;
    private Long itemDealId;
    private DealState dealState;
    private Long wishedMemberId;

    @QueryProjection
    public SellItemDto(Long itemId, String itemImgFile, String title, Integer price, LocalDateTime lastModifiedDate, Long itemCategoryId, String itemCategoryName, String lectureName, String professorName, Long itemDealId, DealState dealState, Long wishedMemberId) {
        this.itemId = itemId;
        this.itemImgFile = itemImgFile;
        this.title = title;
        this.price = price;
        this.lastModifiedDate = lastModifiedDate;
        this.itemCategoryId = itemCategoryId;
        this.itemCategoryName = itemCategoryName;
        this.lectureName = lectureName;
        this.professorName = professorName;
        this.itemDealId = itemDealId;
        this.dealState = dealState;
        this.wishedMemberId = wishedMemberId;
    }
}
