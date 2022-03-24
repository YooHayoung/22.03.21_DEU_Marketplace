package com.deu.marketplace.common;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class ItemSearchCond { // 검색 조건
    private String classification; // 팝니다 or 삽니다
    private String title; // 글제목
    private Long itemCategoryId; // 카테고리 ID
    private String lectureName; // 강의명
    private String professorName; // 교수명
    private Integer priceGoe; // 최소가격
    private Integer priceLoe; // 최대가격
}
