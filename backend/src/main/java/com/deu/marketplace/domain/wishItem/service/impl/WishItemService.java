package com.deu.marketplace.domain.wishItem.service.impl;

import com.querydsl.core.Tuple;

import java.util.Optional;

public interface WishItemService {
    Optional<Tuple> getWishCountAndMyWishByItemId(Long itemId, Long memberId);
}
