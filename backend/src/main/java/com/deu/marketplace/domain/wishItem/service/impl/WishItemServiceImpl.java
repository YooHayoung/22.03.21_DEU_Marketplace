package com.deu.marketplace.domain.wishItem.service.impl;

import com.deu.marketplace.domain.item.entity.Item;
import com.deu.marketplace.domain.member.entity.Member;
import com.deu.marketplace.domain.wishItem.entity.WishItem;
import com.deu.marketplace.domain.wishItem.repository.WishItemRepository;
import com.deu.marketplace.domain.wishItem.service.WishItemService;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WishItemServiceImpl implements WishItemService {

    private final WishItemRepository wishItemRepository;

    @Override
    @Transactional
    public Optional<WishItem> updateWishItem(Item item, Member member) {
        Optional<WishItem> wishItem = wishItemRepository.findByInfo(item.getId(), member.getId());
        if (wishItem.isPresent()) {
            wishItemRepository.delete(wishItem.orElseThrow());
            return Optional.empty();
        } else {
            WishItem savedWishItem =
                    wishItemRepository.save(WishItem.builder().item(item).wishedMember(member).build());
            return Optional.ofNullable(savedWishItem);
        }
    }

    @Override
    public Optional<Tuple> getWishCountAndMyWishByItemId(Long itemId, Long memberId) {
        return wishItemRepository.getWishCountAndMyWishByItemId(itemId, memberId);
    }
}
