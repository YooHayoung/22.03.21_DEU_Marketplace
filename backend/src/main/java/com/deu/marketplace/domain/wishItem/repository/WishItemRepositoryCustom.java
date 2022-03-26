package com.deu.marketplace.domain.wishItem.repository;

import com.querydsl.core.Tuple;

import java.util.List;
import java.util.Optional;

public interface WishItemRepositoryCustom {
    Optional<Tuple> getWishCountAndMyWishByItemId(Long itemId, Long memberId);
}
