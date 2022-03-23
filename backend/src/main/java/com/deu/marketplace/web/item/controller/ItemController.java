package com.deu.marketplace.web.item.controller;

import com.deu.marketplace.common.ItemSearchCond;
import com.deu.marketplace.domain.item.entity.BookState;
import com.deu.marketplace.domain.item.entity.Classification;
import com.deu.marketplace.domain.item.entity.Item;
import com.deu.marketplace.domain.item.service.ItemService;
import com.deu.marketplace.domain.itemCategory.entity.ItemCategory;
import com.deu.marketplace.domain.itemCategory.service.ItemCategoryService;
import com.deu.marketplace.domain.itemImg.entity.ItemImg;
import com.deu.marketplace.domain.itemImg.service.ItemImgService;
import com.deu.marketplace.domain.lecture.entity.Lecture;
import com.deu.marketplace.domain.lecture.service.LectureService;
import com.deu.marketplace.domain.member.service.MemberService;
import com.deu.marketplace.web.item.dto.BuyItemListResponseDto;
import com.deu.marketplace.web.item.dto.ItemDetailResponseDto;
import com.deu.marketplace.web.item.dto.ItemSaveRequestDto;
import com.deu.marketplace.web.item.dto.SellItemListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/items")
public class ItemController {

    private final ItemService itemService;
    private final ItemImgService itemImgService;
    private final ItemCategoryService itemCategoryService;
    private final LectureService lectureService;
    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<?> getItems(ItemSearchCond cond,
                                      @PageableDefault(size = 20, page = 0,
                                              sort = "lastModifiedDate",
                                              direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Item> items = itemService.searchItemPage(cond, pageable);
        if (cond.getClassification().equals(Classification.SELL)) {
            Page<SellItemListResponseDto> dtoPage = items.map(item -> new SellItemListResponseDto(item));
            return ResponseEntity.ok().body(dtoPage);
        } else {
            Page<BuyItemListResponseDto> dtoPage = items.map(item -> new BuyItemListResponseDto(item));
            return ResponseEntity.ok().body(dtoPage);
        }
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<?> getOneItemById(@PathVariable("itemId") Long itemId) {
        Item item = itemService.getOneItemById(itemId).orElseThrow();
        List<ItemImg> findItemImgs = itemImgService.getAllByItemId(itemId);
        ItemDetailResponseDto itemDetailResponseDto =
                new ItemDetailResponseDto(item, findItemImgs);
        return ResponseEntity.ok().body(itemDetailResponseDto);
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveItem(@RequestBody ItemSaveRequestDto requestDto) {
        ItemCategory itemCategory = itemCategoryService
                .searchOneById(requestDto.getItemCategoryId()).orElseThrow(() -> new NoResultException());
        String categoryName = itemCategory.getCategoryName();
        if (categoryName.equals("대학 교재")) {
            Lecture lecture = lectureService
                    .getOneById(requestDto.getLectureId()).orElseThrow(() -> new NoResultException());
            Item item = Item.ByUnivBookBuilder()
                    .member(memberService.getMemberById(requestDto.getMemberId()).orElse(null))
                    .itemCategory(itemCategory)
                    .title(requestDto.getTitle())
                    .lecture(lecture)
                    .bookState(new BookState(requestDto.getWriteState(),
                            requestDto.getSurfaceState(), requestDto.getRegularPrice()))
                    .price(requestDto.getPrice())
                    .description(requestDto.getDescription())
                    .classification(Classification.valueOf(requestDto.getClassification()))
                    .build();
            return  ResponseEntity.ok().body(itemService.saveItem(item));
        } else {
            Item item = Item.ByNormalItemBuilder()
                    .member(memberService.getMemberById(requestDto.getMemberId()).orElse(null))
                    .itemCategory(itemCategory)
                    .title(requestDto.getTitle())
                    .price(requestDto.getPrice())
                    .description(requestDto.getDescription())
                    .build();
            return ResponseEntity.ok().body(itemService.saveItem(item));
        }


    }
}
