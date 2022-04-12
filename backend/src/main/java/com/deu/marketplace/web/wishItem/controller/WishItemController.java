package com.deu.marketplace.web.wishItem.controller;

import com.deu.marketplace.common.ApiResponse;
import com.deu.marketplace.domain.item.entity.Item;
import com.deu.marketplace.domain.item.service.ItemService;
import com.deu.marketplace.domain.member.entity.Member;
import com.deu.marketplace.domain.member.service.MemberService;
import com.deu.marketplace.domain.wishItem.entity.WishItem;
import com.deu.marketplace.domain.wishItem.service.WishItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/wishItem")
public class WishItemController {

    private final ItemService itemService;
    private final MemberService memberService;
    private final WishItemService wishItemService;

    @GetMapping("/{itemId}")
    public ApiResponse setWishItem(@PathVariable("itemId") Long itemId,
                                   @AuthenticationPrincipal Long memberId) {
        Item item = itemService.getOneItemById(itemId).orElseThrow();
        Member member = memberService.getMemberById(memberId).orElseThrow();

        Optional<WishItem> wishItem = wishItemService.updateWishItem(item, member);

        if (wishItem.isPresent()) {
            return ApiResponse.success("result", wishItem.orElse(null).getId());
        } else {
            return ApiResponse.success("result", null);
        }
    }
}
