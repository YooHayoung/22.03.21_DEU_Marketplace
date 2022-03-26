package com.deu.marketplace.web.item.dto;

import com.deu.marketplace.domain.item.entity.BookState;
import com.deu.marketplace.domain.item.entity.Classification;
import com.deu.marketplace.domain.item.entity.Item;
import com.deu.marketplace.domain.itemCategory.entity.ItemCategory;
import com.deu.marketplace.domain.lecture.entity.Lecture;
import com.deu.marketplace.domain.member.entity.Member;
import com.deu.marketplace.web.itemImg.dto.ItemImgRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.NoResultException;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemSaveRequestDto {
    private String classification;
    private List<ItemImgRequestDto> itemImgs;
    private String title;
    private Long itemCategoryId;
    private Long lectureId;
    private BookState bookState;
    private int price;
    private String description;

    public Item toItemEntity(Member member, ItemCategory itemCategory) {
        return Item.ByNormalItemBuilder()
                .classification(Classification.valueOf(classification))
                .itemCategory(itemCategory)
                .title(title)
                .price(price)
                .description(description)
                .member(member)
                .build();
    }

    public Item toItemEntity(Member member, ItemCategory itemCategory, Lecture lecture) {
        if (itemCategory.getCategoryName().equals("대학 교재")) {
            return Item.ByUnivBookBuilder()
                    .classification(Classification.valueOf(classification))
                    .itemCategory(itemCategory)
                    .lecture(lecture)
                    .bookState(bookState)
                    .title(title)
                    .price(price)
                    .description(description)
                    .member(member)
                    .build();
        } else if (itemCategory.getCategoryName().equals("강의 관련 물품")) {
            return Item.ByUnivItemBuilder()
                    .classification(Classification.valueOf(classification))
                    .itemCategory(itemCategory)
                    .lecture(lecture)
                    .title(title)
                    .price(price)
                    .description(description)
                    .member(member)
                    .build();
        } else if (itemCategory.getCategoryName().equals("서적")) {
            return Item.ByBookItemBuilder()
                    .classification(Classification.valueOf(classification))
                    .itemCategory(itemCategory)
                    .bookState(bookState)
                    .title(title)
                    .price(price)
                    .description(description)
                    .member(member)
                    .build();
        } else {
            return toItemEntity(member, itemCategory);
        }
    }
}
