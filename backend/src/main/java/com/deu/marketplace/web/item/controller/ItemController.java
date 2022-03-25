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
import com.deu.marketplace.query.dto.BuyItemDto;
import com.deu.marketplace.query.dto.SellItemDto;
import com.deu.marketplace.query.repository.ItemListRepository;
import com.deu.marketplace.web.item.dto.ItemDetailResponseDto;
import com.deu.marketplace.web.item.dto.ItemSaveRequestDto;
import com.deu.marketplace.web.itemImg.dto.ItemImgRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;
import java.util.ArrayList;
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
    private final ItemListRepository itemListRepository;

    @GetMapping
    public ResponseEntity<?> getItemList(ItemSearchCond cond,
                                         @PageableDefault(size = 20, page = 0,
                                                 sort = "lastModifiedDate",
                                                 direction = Sort.Direction.DESC) Pageable pageable,
                                         @RequestHeader(value = "memberId") Long memberId) {
//        Page<Item> items = itemService.searchItemPage(cond, pageable);
//        if (cond.getClassification().equals(Classification.SELL.name())) {
//            Page<SellItemListResponseDto> dtoPage = items.map(item -> new SellItemListResponseDto(item));
//            return ResponseEntity.ok().body(dtoPage);
//        } else {
//            Page<BuyItemListResponseDto> dtoPage = items.map(item -> new BuyItemListResponseDto(item));
//            return ResponseEntity.ok().body(dtoPage);
//        }

        if (cond.getClassification().equals(Classification.SELL.name())) {
            Page<SellItemDto> sellItemPages = itemListRepository.getSellItemPages(cond, pageable, memberId);
            return ResponseEntity.ok().body(sellItemPages);
        } else {
            Page<BuyItemDto> buyItemPages = itemListRepository.getBuyItemPages(cond, pageable, memberId);
            return ResponseEntity.ok().body(buyItemPages);
        }
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<?> getOneItem(@PathVariable("itemId") Long itemId) {
        Item item = itemService.getOneItemById(itemId).orElseThrow();
        List<ItemImg> findItemImgs = itemImgService.getAllByItemId(itemId);
        ItemDetailResponseDto itemDetailResponseDto =
                new ItemDetailResponseDto(item, findItemImgs);
        return ResponseEntity.ok().body(itemDetailResponseDto);
    }

//    @PatchMapping("/{itemId}")
//    public ResponseEntity<?> updateOneItem(@PathVariable("itemId") Long itemId) {}
//
//    @DeleteMapping("/{itemId}")
//    public ResponseEntity<?> deleteOneItem(@PathVariable("itemId") Long itemId) {}

    @PostMapping("/save")
    public ResponseEntity<?> saveItem(@RequestBody ItemSaveRequestDto requestDto) {
        ItemCategory itemCategory = itemCategoryService
                .searchOneById(requestDto.getItemCategoryId()).orElseThrow(() -> new NoResultException());
        String categoryName = itemCategory.getCategoryName();
        if (requestDto.getClassification().equals(Classification.SELL.name())) {
            if (categoryName.equals("대학 교재")) {
                Lecture lecture = lectureService
                        .getOneById(requestDto.getLectureId()).orElseThrow(() -> new NoResultException());
                Item item = Item.ByUnivBookBuilder()
                        .member(memberService.getMemberById(requestDto.getMemberId()).orElse(null)) //
                        .itemCategory(itemCategory)
                        .title(requestDto.getTitle())
                        .lecture(lecture)
                        .bookState(new BookState(requestDto.getWriteState(),
                                requestDto.getSurfaceState(), requestDto.getRegularPrice()))
                        .price(requestDto.getPrice())
                        .description(requestDto.getDescription())
                        .classification(Classification.valueOf(requestDto.getClassification()))
                        .build();
                itemImgDtosToEntity(requestDto.getItemImgs(), item);
                return ResponseEntity.ok().body(itemService.saveItem(item));
            } else if (categoryName.equals("강의 관련 물품")) {
                Lecture lecture = lectureService
                        .getOneById(requestDto.getLectureId()).orElseThrow(() -> new NoResultException());
                Item item = Item.ByUnivBookBuilder()
                        .member(memberService.getMemberById(requestDto.getMemberId()).orElse(null)) //
                        .itemCategory(itemCategory)
                        .title(requestDto.getTitle())
                        .lecture(lecture)
                        .price(requestDto.getPrice())
                        .description(requestDto.getDescription())
                        .classification(Classification.valueOf(requestDto.getClassification()))
                        .build();
                itemImgDtosToEntity(requestDto.getItemImgs(), item);
                return ResponseEntity.ok().body(itemService.saveItem(item));
            } else if (categoryName.equals("서적")) {
                Item item = Item.ByUnivBookBuilder()
                        .member(memberService.getMemberById(requestDto.getMemberId()).orElse(null)) //
                        .itemCategory(itemCategory)
                        .title(requestDto.getTitle())
                        .bookState(new BookState(requestDto.getWriteState(),
                                requestDto.getSurfaceState(), requestDto.getRegularPrice()))
                        .price(requestDto.getPrice())
                        .description(requestDto.getDescription())
                        .classification(Classification.valueOf(requestDto.getClassification()))
                        .build();
                itemImgDtosToEntity(requestDto.getItemImgs(), item);
                return ResponseEntity.ok().body(itemService.saveItem(item));
            } else {
                Item item = Item.ByNormalItemBuilder()
                        .member(memberService.getMemberById(requestDto.getMemberId()).orElse(null)) //
                        .itemCategory(itemCategory)
                        .title(requestDto.getTitle())
                        .price(requestDto.getPrice())
                        .description(requestDto.getDescription())
                        .classification(Classification.valueOf(requestDto.getClassification()))
                        .build();
                return ResponseEntity.ok().body(itemService.saveItem(item));
            }
        } else {
            Item item = Item.ByNormalItemBuilder()
                    .member(memberService.getMemberById(requestDto.getMemberId()).orElse(null)) //
                    .itemCategory(itemCategory)
                    .title(requestDto.getTitle())
                    .price(requestDto.getPrice())
                    .description(requestDto.getDescription())
                    .classification(Classification.valueOf(requestDto.getClassification()))
                    .build();
            return ResponseEntity.ok().body(itemService.saveItem(item));
        }
    }
    private List<?> itemImgDtosToEntity(List<ItemImgRequestDto> dtos, Item item) {
        List<ItemImg> itemImgs = new ArrayList<>();
        if (dtos != null) {
            for (ItemImgRequestDto dto : dtos) {
                itemImgs.add(ItemImg.builder()
                        .item(item)
                        .imgFile(dto.getImgFile())
                        .imgSeq(dto.getImgSeq())
                        .build());
            }
        }
        return itemImgs;
    }
}
