package com.deu.marketplace.web.item.dto;

import com.deu.marketplace.domain.item.entity.BookState;
import com.deu.marketplace.domain.item.entity.Item;
import com.deu.marketplace.domain.itemCategory.entity.ItemCategory;
import com.deu.marketplace.domain.lecture.entity.Lecture;
import com.deu.marketplace.domain.member.entity.Member;
import lombok.Getter;

@Getter
public class ItemSaveRequestDto {

    private String title;

    private String description;

    private Integer price;

    private Long itemCategoryId;

    private Long lectureId;

    private String writeState;

    private String surfaceState;

    private Integer regularPrice;

    public ItemSaveRequestDto(String title, String description, Integer price,
                              Long itemCategoryId, Long lectureId, String writeState,
                              String surfaceState, Integer regularPrice) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.itemCategoryId = itemCategoryId;
        this.lectureId = lectureId;
        this.writeState = writeState;
        this.surfaceState = surfaceState;
        this.regularPrice = regularPrice;
    }

    public Item toEntity(Member member, ItemCategory itemCategory, Lecture lecture) {
        return Item.ByUnivBookBuilder()
                .writer(member)
                .itemCategory(itemCategory)
                .lecture(lecture)
                .bookState(BookState.builder()
                        .surfaceState(surfaceState)
                        .writeState(writeState)
                        .regularPrice(regularPrice)
                        .build())
                .title(title)
                .price(price)
                .description(description)
                .build();
    }

    public Item toEntity(Member member, ItemCategory itemCategory) {
        if (itemCategory.getCategoryName().equals("서적")) {
            return Item.ByBookItemBuilder()
                    .writer(member)
                    .itemCategory(itemCategory)
                    .bookState(BookState.builder()
                            .surfaceState(surfaceState)
                            .writeState(writeState)
                            .regularPrice(regularPrice)
                            .build())
                    .title(title)
                    .price(price)
                    .description(description)
                    .build();
        }
        return Item.ByNormalItemBuilder()
                .writer(member)
                .itemCategory(itemCategory)
                .title(title)
                .price(price)
                .description(description)
                .build();
    }
}
