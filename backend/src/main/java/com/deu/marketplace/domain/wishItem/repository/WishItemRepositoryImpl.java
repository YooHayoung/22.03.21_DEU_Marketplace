package com.deu.marketplace.domain.wishItem.repository;

import com.deu.marketplace.domain.wishItem.entity.QWishItem;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.deu.marketplace.domain.wishItem.entity.QWishItem.wishItem;

public class WishItemRepositoryImpl implements WishItemRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public WishItemRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

//    SELECT wish_item.item_id, COUNT(*), (SELECT case
//    when EXISTS (SELECT * FROM wish_item WHERE wish_item.wished_member_id = 2 AND wish_item.item_id = 1)
//    then 1
//    ELSE 0
//    END)
//    FROM wish_item
//    WHERE wish_item.item_id = 1
//    GROUP BY wish_item.item_id

    @Override
    public Optional<Tuple> getWishCountAndMyWishByItemId(Long itemId, Long memberId) {
        Tuple result = queryFactory
                .select(wishItem.item.id.as("itemId"),
                        wishItem.count().as("wishCount"),
                        (JPAExpressions
                                .select(wishItem)
                                .from(wishItem)
                                .where(wishItem.wishedMember.id.eq(memberId)
                                        .and(wishItem.item.id.eq(itemId)))).exists().as("myWish"))
                .from(wishItem)
                .where(wishItem.item.id.eq(itemId))
                .groupBy(wishItem.item)
                .fetchOne();

        return Optional.ofNullable(result);
    }
}
