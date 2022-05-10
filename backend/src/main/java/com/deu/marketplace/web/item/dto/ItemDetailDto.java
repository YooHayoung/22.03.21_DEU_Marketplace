package com.deu.marketplace.web.item.dto;

import com.deu.marketplace.domain.item.entity.BookState;
import com.deu.marketplace.domain.item.entity.Classification;
import com.deu.marketplace.domain.item.entity.Item;
import com.deu.marketplace.web.itemCategory.dto.ItemCategoryDto;
import com.deu.marketplace.web.lecture.dto.LectureDto;
import lombok.Builder;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class ItemDetailDto {
    private Long itemId;
    private Classification classification;
    private ItemCategoryDto itemCategoryInfo;
    private LectureDto lectureInfo;
    private BookState bookStateInfo;
    private String title;
    private int price;
    private String description;
    private MemberShortInfoDto sellerInfo;
    private String lastModifiedDate;

    @Builder
    public ItemDetailDto(Item item) {
        this.itemId = item.getId();
        this.classification = item.getClassification();
        this.itemCategoryInfo = ItemCategoryDto.builder()
                .itemCategory(item.getItemCategory())
                .build();
        if (item.getLecture() != null) {
            this.lectureInfo = LectureDto.builder().lecture(item.getLecture()).build();
        }
        if (item.getBookState() != null) {
            this.bookStateInfo = BookState.builder()
                    .writeState(item.getBookState().getWriteState())
                    .surfaceState(item.getBookState().getSurfaceState())
                    .regularPrice(item.getBookState().getRegularPrice())
                    .build();
        }
        this.title = item.getTitle();
        this.price = item.getPrice();
        this.description = item.getDescription();
        this.sellerInfo = MemberShortInfoDto.builder().member(item.getMember()).build();
        this.lastModifiedDate = item.getLastModifiedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
