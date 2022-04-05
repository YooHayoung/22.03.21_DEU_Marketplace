package com.deu.marketplace.web.item.controller;

import com.deu.marketplace.common.ItemSearchCond;
import com.deu.marketplace.domain.chatRoom.entity.ChatRoom;
import com.deu.marketplace.domain.chatRoom.service.ChatRoomService;
import com.deu.marketplace.domain.deal.entity.Deal;
import com.deu.marketplace.domain.deal.service.DealService;
import com.deu.marketplace.domain.item.entity.Classification;
import com.deu.marketplace.domain.item.entity.Item;
import com.deu.marketplace.domain.item.service.ItemService;
import com.deu.marketplace.domain.itemCategory.entity.ItemCategory;
import com.deu.marketplace.domain.itemCategory.service.ItemCategoryService;
import com.deu.marketplace.domain.itemImg.entity.ItemImg;
import com.deu.marketplace.domain.itemImg.service.ItemImgService;
import com.deu.marketplace.domain.lecture.entity.Lecture;
import com.deu.marketplace.domain.lecture.service.LectureService;
import com.deu.marketplace.domain.member.entity.Member;
import com.deu.marketplace.domain.member.service.MemberService;
import com.deu.marketplace.domain.wishItem.service.impl.WishItemService;
import com.deu.marketplace.query.item.dto.BuyItemDto;
import com.deu.marketplace.query.item.dto.SellItemDto;
import com.deu.marketplace.query.item.repository.ItemViewRepository;
import com.deu.marketplace.web.item.dto.ItemDetailResponseDto;
import com.deu.marketplace.web.item.dto.ItemSaveRequestDto;
import com.deu.marketplace.web.itemImg.dto.ItemImgRequestDto;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;
import javax.xml.bind.ValidationException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/items")
public class ItemController {

    private final ItemService itemService;
    private final ItemImgService itemImgService;
    private final ItemCategoryService itemCategoryService;
    private final LectureService lectureService;
    private final MemberService memberService;
    private final DealService dealService;
    private final WishItemService wishItemService;
    private final ChatRoomService chatRoomService;

    private final ItemViewRepository itemViewRepository;

    @GetMapping
    public ResponseEntity<?> getItemList(ItemSearchCond cond,
                                         @PageableDefault(size = 20, page = 0,
                                                 sort = "lastModifiedDate",
                                                 direction = Sort.Direction.DESC) Pageable pageable,
                                         @AuthenticationPrincipal Long memberId) {
        if (cond.getClassification().equals(Classification.SELL.name())) {
            Page<SellItemDto> sellItemPages = itemViewRepository.getSellItemPages(cond, pageable, memberId);
            return ResponseEntity.ok().body(sellItemPages);
        } else {
            Page<BuyItemDto> buyItemPages = itemViewRepository.getBuyItemPages(cond, pageable, memberId);
            return ResponseEntity.ok().body(buyItemPages);
        }
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<?> getOneItem(@PathVariable("itemId") Long itemId,
                                        @AuthenticationPrincipal Long memberId) {
        Item item = itemService.getOneItemInfoById(itemId).orElseThrow();
        Optional<Tuple> wishInfo = wishItemService.getWishCountAndMyWishByItemId(itemId, memberId);
        Optional<Deal> deal = dealService.getOneByItemId(item.getId());
//
        List<ItemImg> findItemImgs = itemImgService.getAllByItemId(itemId);
        ChatRoom chatRoom = chatRoomService
                .getOneChatRoomByItemIdAndMemberId(itemId, memberId).orElse(null);
        ItemDetailResponseDto itemDetailResponseDto =
                ItemDetailResponseDto.builder()
                        .item(item)
                        .wishInfo(wishInfo)
                        .deal(deal)
                        .chatRoom(chatRoom)
                        .itemImgs(findItemImgs)
                        .build();
        return ResponseEntity.ok().body(itemDetailResponseDto);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<?> updateOneItem(@PathVariable("itemId") Long itemId,
                                           @AuthenticationPrincipal Long memberId,
                                           @RequestBody ItemSaveRequestDto requestDto) throws Exception {
        Item item = requestInfoToEntities(requestDto, memberId);
        Item updateItem = itemService.updateItem(itemId, item, memberId);

        URI redirectUri = new URI("http://localhost:8080/api/v1/items/" + updateItem.getId());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(redirectUri);
        return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<?> deleteOneItem(@PathVariable("itemId") Long itemId,
                                           @AuthenticationPrincipal Long memberId) throws URISyntaxException, ValidationException {
        itemService.deleteItem(itemId, memberId);

        URI redirectUri = new URI("http://localhost:8080/api/v1/items/");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(redirectUri);
        return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveItem(@RequestBody ItemSaveRequestDto requestDto,
                                      @AuthenticationPrincipal Long memberId) throws URISyntaxException {
        Item item = requestInfoToEntities(requestDto, memberId);
        item = itemService.saveItem(item);

        URI redirectUri = new URI("http://localhost:8080/api/v1/items/" + item.getId());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(redirectUri);
        return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
    }

    private Item requestInfoToEntities(@RequestBody ItemSaveRequestDto requestDto,
                                       @AuthenticationPrincipal Long memberId) {
        Member member = memberService.getMemberById(memberId).orElseThrow(NoResultException::new);
        ItemCategory itemCategory = itemCategoryService
                .searchOneById(requestDto.getItemCategoryId()).orElseThrow(NoResultException::new);

        Item item;
        if (requestDto.getClassification().equals(Classification.SELL.name())) {
            if (requestDto.getLectureId() != null) {
                Lecture lecture = lectureService.getOneById(requestDto.getLectureId()).orElse(null);
                item = requestDto.toItemEntity(member, itemCategory, lecture);
            } else {
                item = requestDto.toItemEntity(member, itemCategory, null);
            }
        } else {
            item = requestDto.toItemEntity(member, itemCategory);
        }
        List<ItemImg> itemImgs = itemImgDtosToEntity(requestDto.getItemImgs(), item);

        return item;
    }

    private List<ItemImg> itemImgDtosToEntity(List<ItemImgRequestDto> dtos, Item item) {
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
