package com.deu.marketplace.web.itemCategory.dto;

import lombok.Getter;

@Getter
public class ItemCategoryResponseDto {

    private Long id;
    private String categoryName;

    public ItemCategoryResponseDto(Long id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }
}
