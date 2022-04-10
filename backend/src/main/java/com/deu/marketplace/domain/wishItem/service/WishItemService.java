package com.deu.marketplace.domain.wishItem.service;

import com.deu.marketplace.domain.item.entity.Item;
import com.deu.marketplace.domain.member.entity.Member;
import com.deu.marketplace.domain.wishItem.entity.WishItem;
import com.querydsl.core.Tuple;

import java.util.Optional;

public interface WishItemService {
    Optional<Tuple> getWishCountAndMyWishByItemId(Long itemId, Long memberId);

    Optional<WishItem> updateWishItem(Item item, Member member);
}
