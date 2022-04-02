package com.deu.marketplace.common;

import com.deu.marketplace.domain.item.entity.Classification;
import lombok.Getter;

@Getter
public class ItemSearchCond { // 검색 조건
    private String classification; // 팝니다 or 삽니다
    private String title; // 글제목
    private Long itemCategoryId; // 카테고리 ID
    private String lectureName; // 강의명
    private String professorName; // 교수명
    private Integer priceGoe; // 최소가격
    private Integer priceLoe; // 최대가격

    public ItemSearchCond(String classification, String title, Long itemCategoryId,
                          String lectureName, String professorName,
                          Integer priceGoe, Integer priceLoe) {
        if (classification == null) this.classification = Classification.SELL.name();
        else this.classification = classification;
        this.title = title;
        this.itemCategoryId = itemCategoryId;
        this.lectureName = lectureName;
        this.professorName = professorName;
        this.priceGoe = priceGoe;
        this.priceLoe = priceLoe;
    }
}
