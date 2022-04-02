package com.deu.marketplace.web.itemCategory.dto;

import com.deu.marketplace.domain.itemCategory.entity.ItemCategory;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ItemCategoryDto {
    private Long id;
    private String categoryName;

    @Builder
    public ItemCategoryDto(ItemCategory itemCategory) {
        this.id = itemCategory.getId();
        this.categoryName = itemCategory.getCategoryName();
    }
}
