package com.deu.marketplace.web.itemCategory.dto;

import com.deu.marketplace.domain.itemCategory.entity.ItemCategory;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemCategoryDto {
    private Long itemCategoryId;
    private String itemCategoryName;

    @Builder
    public ItemCategoryDto(ItemCategory itemCategory) {
        this.itemCategoryId = itemCategory.getId();
        this.itemCategoryName = itemCategory.getCategoryName();
    }

    public ItemCategory toEntity() {
        return ItemCategory.dtoToEntityBuilder()
                .id(itemCategoryId)
                .categoryName(itemCategoryName)
                .build();
    }
}
