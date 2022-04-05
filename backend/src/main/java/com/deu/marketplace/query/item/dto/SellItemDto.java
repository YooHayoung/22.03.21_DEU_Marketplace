package com.deu.marketplace.query.item.dto;

import com.deu.marketplace.domain.deal.entity.DealState;
import com.deu.marketplace.domain.item.entity.Classification;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class SellItemDto {
    private Long itemId;
    private Classification classification;
    private String itemImgFile;
    private String title;
    private Integer price;
    private String lastModifiedDate;
    private Long itemCategoryId;
    private String itemCategoryName;
    private String lectureName;
    private String professorName;
    private Long itemDealId;
    private DealState dealState;
    private Long wishedMemberId;

    @QueryProjection
    public SellItemDto(Long itemId, Classification classification, String itemImgFile, String title, Integer price, LocalDateTime lastModifiedDate, Long itemCategoryId, String itemCategoryName, String lectureName, String professorName, Long itemDealId, DealState dealState, Long wishedMemberId) {
        this.itemId = itemId;
        this.classification = classification;
        this.itemImgFile = itemImgFile;
        this.title = title;
        this.price = price;
        this.lastModifiedDate = lastModifiedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.itemCategoryId = itemCategoryId;
        this.itemCategoryName = itemCategoryName;
        this.lectureName = lectureName;
        this.professorName = professorName;
        this.itemDealId = itemDealId;
        this.dealState = dealState;
        this.wishedMemberId = wishedMemberId;
    }
}
