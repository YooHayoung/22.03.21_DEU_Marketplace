package com.deu.marketplace.web.itemCategory.dto;

import com.deu.marketplace.domain.itemCategory.entity.ItemCategory;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemCategoryDto {
    private Long categoryId;
    private String categoryName;

    @Builder
    public ItemCategoryDto(ItemCategory itemCategory) {
        this.categoryId = itemCategory.getId();
        this.categoryName = itemCategory.getCategoryName();
    }

    public ItemCategory toEntity() {
        return ItemCategory.dtoToEntityBuilder()
                .id(categoryId)
                .categoryName(categoryName)
                .build();
    }
}
