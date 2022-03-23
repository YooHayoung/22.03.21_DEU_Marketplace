package com.deu.marketplace.web.item.dto;

import com.deu.marketplace.domain.item.entity.Item;
import com.deu.marketplace.web.lecture.dto.LectureDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BuyItemListResponseDto {
    private Long itemId;
    private String itemImgFile;
    private String title;
    private int price;
    private LocalDateTime lastModifiedDate;
    private String dealState;
    private boolean myWish;

    public BuyItemListResponseDto(Item item) {
        this.itemId = item.getId();
        this.title = item.getTitle();
        this.price = item.getPrice();
        this.lastModifiedDate = item.getLastModifiedDate();
    }
}
