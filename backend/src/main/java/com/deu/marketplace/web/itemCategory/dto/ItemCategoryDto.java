package com.deu.marketplace.web.itemCategory.dto;

import com.deu.marketplace.domain.itemCategory.entity.ItemCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class ItemCategoryDto {
    private Long id;
    private String categoryName;


    public ItemCategoryDto(ItemCategory itemCategory) {
        this.id = itemCategory.getId();
        this.categoryName = itemCategory.getCategoryName();
    }
}
