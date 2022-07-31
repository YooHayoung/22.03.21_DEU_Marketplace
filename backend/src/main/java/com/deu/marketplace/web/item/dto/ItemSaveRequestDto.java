package com.deu.marketplace.web.item.dto;

import com.deu.marketplace.domain.item.entity.BookState;
import com.deu.marketplace.domain.item.entity.Classification;
import com.deu.marketplace.domain.item.entity.Item;
import com.deu.marketplace.domain.member.entity.Member;
import com.deu.marketplace.web.itemCategory.dto.ItemCategoryDto;
import com.deu.marketplace.web.lecture.dto.LectureDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemSaveRequestDto {
    private String classification;
    private String title;
    private ItemCategoryDto itemCategoryInfo;
    private LectureDto lectureInfo;
    private BookState bookStateInfo;
    private int price;
    private String description;
//    private List<ItemImgRequestDto> itemImgs;

    public Item toItemEntity(Member member) {
        if (classification.equals(Classification.SELL.name())) {
            if (itemCategoryInfo.getCategoryName().equals("대학 교재")) {
                return Item.ByUnivBookBuilder()
                        .classification(Classification.valueOf(classification))
                        .itemCategory(itemCategoryInfo.toEntity())
                        .lecture(lectureInfo.toEntity())
                        .bookState(bookStateInfo)
                        .title(title)
                        .price(price)
                        .description(description)
                        .member(member)
                        .build();
            } else if (itemCategoryInfo.getCategoryName().equals("강의 관련 물품")) {
                return Item.ByUnivItemBuilder()
                        .classification(Classification.valueOf(classification))
                        .itemCategory(itemCategoryInfo.toEntity())
                        .lecture(lectureInfo.toEntity())
                        .title(title)
                        .price(price)
                        .description(description)
                        .member(member)
                        .build();
            } else if (itemCategoryInfo.getCategoryName().equals("서적")) {
                return Item.ByBookItemBuilder()
                        .classification(Classification.valueOf(classification))
                        .itemCategory(itemCategoryInfo.toEntity())
                        .bookState(bookStateInfo)
                        .title(title)
                        .price(price)
                        .description(description)
                        .member(member)
                        .build();
            } else {
                return Item.ByNormalItemBuilder()
                        .classification(Classification.valueOf(classification))
                        .itemCategory(itemCategoryInfo.toEntity())
                        .title(title)
                        .price(price)
                        .description(description)
                        .member(member)
                        .build();
            }
        } else {
            return Item.ByNormalItemBuilder()
                    .classification(Classification.valueOf(classification))
                    .itemCategory(itemCategoryInfo.toEntity())
                    .title(title)
                    .price(price)
                    .description(description)
                    .member(member)
                    .build();
        }
    }
}
