package com.deu.marketplace.web.item.controller;

import com.deu.marketplace.common.ApiResponse;
import com.deu.marketplace.common.ItemSearchCond;
import com.deu.marketplace.domain.chatRoom.entity.ChatRoom;
import com.deu.marketplace.domain.chatRoom.service.ChatRoomService;
import com.deu.marketplace.domain.deal.entity.Deal;
import com.deu.marketplace.domain.deal.service.DealService;
import com.deu.marketplace.domain.item.entity.Classification;
import com.deu.marketplace.domain.item.entity.Item;
import com.deu.marketplace.domain.item.service.ItemService;
import com.deu.marketplace.domain.itemCategory.service.ItemCategoryService;
import com.deu.marketplace.domain.itemImg.entity.ItemImg;
import com.deu.marketplace.domain.itemImg.service.ItemImgService;
import com.deu.marketplace.domain.lecture.service.LectureService;
import com.deu.marketplace.domain.member.entity.Member;
import com.deu.marketplace.domain.member.service.MemberService;
import com.deu.marketplace.domain.wishItem.service.WishItemService;
import com.deu.marketplace.query.item.dto.BuyItemDto;
import com.deu.marketplace.query.item.dto.SellItemDto;
import com.deu.marketplace.query.item.repository.ItemViewRepository;
import com.deu.marketplace.s3.S3Uploader;
import com.deu.marketplace.web.item.dto.ItemDetailResponseDto;
import com.deu.marketplace.web.item.dto.ItemSaveRequestDto;
import com.deu.marketplace.web.itemImg.dto.ItemImgRequestDto;
import com.deu.marketplace.web.itemImg.dto.ItemImgResponseDto;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;
import javax.xml.bind.ValidationException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/items")
public class ItemController {

    private final ItemService itemService;
    private final ItemImgService itemImgService;
    private final MemberService memberService;
    private final DealService dealService;
    private final WishItemService wishItemService;
    private final ChatRoomService chatRoomService;
    private final S3Uploader s3Uploader;

    private final ItemViewRepository itemViewRepository;

    @GetMapping
    public ApiResponse getItemList(ItemSearchCond cond,
                                   @PageableDefault(size = 20, page = 0,
                                                 sort = "lastModifiedDate",
                                                 direction = Sort.Direction.DESC) Pageable pageable,
                                   @AuthenticationPrincipal Long memberId) {
        if (cond.getClassification().equals(Classification.SELL.name())) {
            Page<SellItemDto> sellItemPages = itemViewRepository.getSellItemPages(cond, pageable, memberId);
            for (SellItemDto sellItemPage : sellItemPages) {
                if (sellItemPage.isItemImgFile())
                    sellItemPage.imgFileToImgUrl(s3Uploader.toUrl(sellItemPage.getItemImgFile()));
            }
            return ApiResponse.success("result", sellItemPages);
        } else {
            Page<BuyItemDto> buyItemPages = itemViewRepository.getBuyItemPages(cond, pageable, memberId);
            for (BuyItemDto buyItemPage : buyItemPages) {
                if (buyItemPage.isItemImgFile())
                    buyItemPage.imgFileToImgUrl(s3Uploader.toUrl(buyItemPage.getItemImgFile()));
            }
            return ApiResponse.success("result", buyItemPages);
        }
    }

    @GetMapping("/{itemId}")
    public ApiResponse getOneItem(@PathVariable("itemId") Long itemId,
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

        for (ItemImgResponseDto itemImgResponseDto : itemDetailResponseDto.getImgList()) {
            itemImgResponseDto.imgToImgUrl(s3Uploader.toUrl(itemImgResponseDto.getImg()));
        }

        return ApiResponse.success("result", itemDetailResponseDto);
    }

    @PatchMapping("/{itemId}")
    public ApiResponse updateOneItem(@PathVariable("itemId") Long itemId,
                                     @AuthenticationPrincipal Long memberId,
                                     @RequestBody ItemSaveRequestDto requestDto) throws Exception {
        Item item = requestInfoToEntities(requestDto, memberId);
        Item updateItem = itemService.updateItem(itemId, item, memberId);

        return ApiResponse.success("result", updateItem.getId());
    }

    // TODO 삭제 수정 예정
    @DeleteMapping("/{itemId}")
    public ApiResponse deleteOneItem(@PathVariable("itemId") Long itemId,
                                           @AuthenticationPrincipal Long memberId) throws ValidationException {
        itemService.deleteItem(itemId, memberId);

        return ApiResponse.success("result", null);
    }

    @PostMapping("/save")
    public ApiResponse saveItem(@RequestBody ItemSaveRequestDto requestDto,
                                @AuthenticationPrincipal Long memberId) {
        Item item = requestInfoToEntities(requestDto, memberId);
        item = itemService.saveItem(item);

        return ApiResponse.success("result", item.getId());
    }

    private Item requestInfoToEntities(@RequestBody ItemSaveRequestDto requestDto,
                                       @AuthenticationPrincipal Long memberId) {
        Member member = memberService.getMemberById(memberId).orElseThrow(NoResultException::new);

        Item item = requestDto.toItemEntity(member);
//        List<ItemImg> itemImgs = itemImgDtosToEntity(requestDto.getItemImgs(), item);

        return item;
    }

    private List<ItemImg> itemImgDtosToEntity(List<ItemImgRequestDto> dtos, Item item) {
        List<ItemImg> itemImgs = new ArrayList<>();
        if (dtos != null) {
            for (ItemImgRequestDto dto : dtos) {
                itemImgs.add(ItemImg.builder()
                        .item(item)
                        .imgFile(dto.getItemImgFile())
                        .imgSeq(dto.getItemImgSeq())
                        .build());
            }
        }
        return itemImgs;
    }
}
