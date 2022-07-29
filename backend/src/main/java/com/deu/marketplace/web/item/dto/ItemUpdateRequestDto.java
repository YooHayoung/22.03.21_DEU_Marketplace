package com.deu.marketplace.web.item.dto;

import com.deu.marketplace.domain.item.entity.BookState;
import com.deu.marketplace.domain.item.entity.Item;
import com.deu.marketplace.domain.itemCategory.entity.ItemCategory;
import com.deu.marketplace.domain.lecture.entity.Lecture;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ItemUpdateRequestDto {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @Max(99999999)
    private Integer price;

    @NotNull
    private Long itemCategoryId;

    private Long lectureId;

    private String writeState;

    private String surfaceState;

    private int regularPrice;

    private List<Long> delImgIdList = new ArrayList<>();

    public ItemUpdateRequestDto(String title, String description, Integer price,
                                Long itemCategoryId, Long lectureId, String writeState,
                                String surfaceState, Integer regularPrice, List<Long> delImgIdList) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.itemCategoryId = itemCategoryId;
        this.lectureId = lectureId;
        this.writeState = writeState;
        this.surfaceState = surfaceState;
        this.regularPrice = regularPrice;
        this.delImgIdList = delImgIdList;
    }

    public Item toEntity(ItemCategory itemCategory, Lecture lecture) {
        return Item.ByUnivBookBuilder()
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

    public Item toEntity(ItemCategory itemCategory) {
        if (itemCategory.getCategoryName().equals("서적")) {
            return Item.ByBookItemBuilder()
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
                .itemCategory(itemCategory)
                .title(title)
                .price(price)
                .description(description)
                .build();
    }

    public void setDelImgIdList(List<Long> delImgIdList) {
        this.delImgIdList = delImgIdList;
    }
}
