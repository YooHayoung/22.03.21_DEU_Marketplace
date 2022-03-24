package com.deu.marketplace.web.item.dto;

import com.deu.marketplace.domain.item.entity.BookState;
import com.deu.marketplace.domain.item.entity.Classification;
import com.deu.marketplace.domain.item.entity.Item;
import com.deu.marketplace.domain.itemImg.entity.ItemImg;
import com.deu.marketplace.web.itemImg.dto.ItemImgResponseDto;
import com.deu.marketplace.web.lecture.dto.LectureDto;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ItemDetailResponseDto {
    private Long itemId;
    private String itemCategoryName;
    private LectureDto lecture;
//    private String lectureName;
//    private String professorName;
    private BookState bookInfo;
    private String title;
    private int price;
    private String description;
    private String memberNickName;
    private Classification classification;
    private List<ItemImgResponseDto> itemImgResponseDtos;
    // wish
    // deal


    public ItemDetailResponseDto(Item item, List<ItemImg> itemImgs) {
        this.itemId = item.getId();
        this.itemCategoryName = item.getItemCategory().getCategoryName();
//        this.lectureName = item.getLecture().getLectureName();
//        this.professorName = item.getLecture().getProfessorName();
        if (item.getLecture() != null) this.lecture = new LectureDto(item.getLecture());
        if (item.getBookState() != null) {
            this.bookInfo = BookState.builder()
                    .writeState(item.getBookState().getWriteState())
                    .surfaceState(item.getBookState().getSurfaceState())
                    .regularPrice(item.getBookState().getRegularPrice())
                    .build();
        }
        this.title = item.getTitle();
        this.price = item.getPrice();
        this.description = item.getDescription();
        this.memberNickName = item.getMember().getNickname();
        this.classification = item.getClassification();
        this.itemImgResponseDtos = itemImgs.stream().map(ItemImgResponseDto::new).collect(Collectors.toList());
    }
}
