package com.deu.marketplace.query.item.repository;

import com.deu.marketplace.common.ItemSearchCond;
import com.deu.marketplace.domain.deal.entity.DealState;
import com.deu.marketplace.domain.item.entity.Classification;
import com.deu.marketplace.query.item.dto.BuyItemDto;
import com.deu.marketplace.query.item.dto.QBuyItemDto;
import com.deu.marketplace.query.item.dto.QSellItemDto;
import com.deu.marketplace.query.item.dto.SellItemDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.deu.marketplace.domain.deal.entity.QDeal.deal;
import static com.deu.marketplace.domain.item.entity.QItem.item;
import static com.deu.marketplace.domain.itemImg.entity.QItemImg.itemImg;
import static com.deu.marketplace.domain.wishItem.entity.QWishItem.wishItem;
import static org.apache.logging.log4j.util.Strings.isEmpty;

@Repository
public class ItemViewRepository {

    private final JPAQueryFactory queryFactory;

    public ItemViewRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

//    SELECT *
//    FROM item LEFT JOIN item_img ON item.item_id = item_img.item_id
//    LEFT JOIN item_deal ON item_deal.item_id = item.item_id
//    LEFT JOIN wish_item ON wish_item.item_id = item.item_id AND wish_item.wished_member_id = 6
//    WHERE (item_img.img_seq = 1
//            OR item_img.img_seq IS NULL)
//    AND item.classification = 'SELL'
//    AND (item_deal.deal_state != '거래완료'
//            OR item_deal.deal_state IS NULL);

    public Page<SellItemDto> getSellItemPages(ItemSearchCond cond, Pageable pageable, Long memberId) {
        List<SellItemDto> content = queryFactory
                .select(new QSellItemDto(
                        item.id,
                        item.classification,
                        itemImg.imgFile,
                        item.title,
                        item.price,
                        item.lastModifiedDate,
                        item.itemCategory.id,
                        item.itemCategory.categoryName,
                        item.lecture.lectureName,
                        item.lecture.professorName,
                        deal.id,
                        deal.dealState,
                        wishItem.wishedMember.id))
                .from(item)
                .leftJoin(itemImg).on(item.eq(itemImg.item))
                .leftJoin(deal).on(item.eq(deal.item))
                .leftJoin(wishItem).on(item.eq(wishItem.item).and(wishItem.wishedMember.id.eq(memberId)))
                .fetchJoin()
                .where((itemImg.imgSeq.eq(1).or(itemImg.imgSeq.isNull()))
                        .and(((deal.dealState.ne(DealState.valueOf(DealState.COMPLETE.name()))
                                .and(deal.dealState.ne(DealState.valueOf(DealState.CANCEL.name())))) // ..
                                .or(deal.dealState.isNull()))),
                        classificationEq(cond.getClassification()),
                        itemCategoryEq(cond.getItemCategoryId()),
                        itemTitleContains(cond.getTitle()),
                        lectureNameContains(cond.getLectureName()),
                        professorNameContains(cond.getProfessorName()),
                        priceGoe(cond.getPriceGoe()),
                        priceLoe(cond.getPriceLoe()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(item.count())
                .from(item)
                .leftJoin(deal).on(item.eq(deal.item))
                .where(((deal.dealState.ne(DealState.valueOf(DealState.COMPLETE.name()))
                                .and(deal.dealState.ne(DealState.valueOf(DealState.CANCEL.name()))))//
                                .or(deal.dealState.isNull())),
                        classificationEq(cond.getClassification()),
                        itemCategoryEq(cond.getItemCategoryId()),
                        itemTitleContains(cond.getTitle()),
                        lectureNameContains(cond.getLectureName()),
                        professorNameContains(cond.getProfessorName()),
                        priceGoe(cond.getPriceGoe()),
                        priceLoe(cond.getPriceLoe())
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    public Page<BuyItemDto> getBuyItemPages(ItemSearchCond cond, Pageable pageable, Long memberId) {
        List<BuyItemDto> content = queryFactory
                .select(new QBuyItemDto(
                        item.id,
                        item.classification,
                        itemImg.imgFile,
                        item.title,
                        item.price,
                        item.lastModifiedDate,
                        deal.id,
                        deal.dealState,
                        wishItem.wishedMember.id))
                .from(item)
                .leftJoin(itemImg).on(item.eq(itemImg.item))
                .leftJoin(deal).on(item.eq(deal.item))
                .leftJoin(wishItem).on(item.eq(wishItem.item).and(wishItem.wishedMember.id.eq(memberId)))
                .where((itemImg.imgSeq.eq(1).or(itemImg.imgSeq.isNull()))
                                .and(((deal.dealState.ne(DealState.valueOf(DealState.COMPLETE.name()))
                                        .and(deal.dealState.ne(DealState.valueOf(DealState.CANCEL.name()))))
                                        .or(deal.dealState.isNull()))),
                        classificationEq(cond.getClassification()),
                        itemCategoryEq(cond.getItemCategoryId()),
                        itemTitleContains(cond.getTitle()),
                        lectureNameContains(cond.getLectureName()),
                        professorNameContains(cond.getProfessorName()),
                        priceGoe(cond.getPriceGoe()),
                        priceLoe(cond.getPriceLoe()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(item.count())
                .from(item)
                .leftJoin(deal).on(item.eq(deal.item))
                .where(((deal.dealState.ne(DealState.valueOf(DealState.COMPLETE.name())))
                                .and(deal.dealState.ne(DealState.valueOf(DealState.CANCEL.name())))
                                .or(deal.dealState.isNull())),
                        classificationEq(cond.getClassification()),
                        itemCategoryEq(cond.getItemCategoryId()),
                        itemTitleContains(cond.getTitle()),
                        lectureNameContains(cond.getLectureName()),
                        professorNameContains(cond.getProfessorName()),
                        priceGoe(cond.getPriceGoe()),
                        priceLoe(cond.getPriceLoe())
                );

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression classificationEq(String classification) {
        return isEmpty(classification) ? null : item.classification.eq(Classification.valueOf(classification));
    }

    private BooleanExpression itemCategoryEq(Long itemCategoryId) {
        return itemCategoryId == null ? null : item.itemCategory.id.eq(itemCategoryId);
    }

    private BooleanExpression itemTitleContains(String title) {
        return isEmpty(title) ? null : item.title.contains(title);
    }

    private BooleanExpression lectureNameContains(String lectureName) {
        return isEmpty(lectureName) ? null : item.lecture.lectureName.contains(lectureName);
    }

    private BooleanExpression professorNameContains(String professorName) {
        return isEmpty(professorName) ? null : item.lecture.professorName.contains(professorName);
    }
    private BooleanExpression priceGoe(Integer priceGoe) {
        return priceGoe == null ? null : item.price.goe(priceGoe);
    }

    private BooleanExpression priceLoe(Integer priceLoe) {
        return priceLoe == null ? null : item.price.loe(priceLoe);
    }
}
