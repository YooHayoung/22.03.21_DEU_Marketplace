package com.deu.marketplace.domain.wishItem.service.impl;

import com.querydsl.core.Tuple;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class WishItemServiceTest {

    @Autowired
    WishItemService wishItemService;

    @Test
    public void 찜개수와_회원찜여부_확인() throws Exception {
        // given
        Long itemId = 5L;
        Long memberId = 3L;

        // when
        Optional<Tuple> wishCountAndMyWish = wishItemService.getWishCountAndMyWishByItemId(itemId, memberId);

        // then
        if (!wishCountAndMyWish.isEmpty()) {
            Tuple tuple = wishCountAndMyWish.orElse(null);
            System.out.println("itemId = " + tuple.get(0, Long.class));
            System.out.println("wishCount = " + tuple.get(1, Integer.class));
            System.out.println("myWish = " + tuple.get(2, Boolean.class));
        } else {
            System.out.println("EMPTY!!");
        }
    }
}