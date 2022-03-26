package com.deu.marketplace.domain.wishItem.service.impl;

import com.deu.marketplace.domain.wishItem.repository.WishItemRepository;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WishItemServiceImpl implements WishItemService {

    private final WishItemRepository wishItemRepository;

    @Override
    public Optional<Tuple> getWishCountAndMyWishByItemId(Long itemId, Long memberId) {
        return wishItemRepository.getWishCountAndMyWishByItemId(itemId, memberId);
    }
}
