package com.deu.marketplace.domain.item.repository;

import com.deu.marketplace.common.ItemSearchCond;
import com.deu.marketplace.domain.item.entity.Classification;
import com.deu.marketplace.domain.item.entity.Item;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.deu.marketplace.domain.item.entity.QItem.item;
import static com.deu.marketplace.domain.itemCategory.entity.QItemCategory.itemCategory;
import static com.deu.marketplace.domain.lecture.entity.QLecture.lecture;
import static com.deu.marketplace.domain.member.entity.QMember.member;
import static org.apache.logging.log4j.util.Strings.isEmpty;

public class ItemRepositoryImpl implements ItemRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public  ItemRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Item> searchItemPage(ItemSearchCond cond, Pageable pageable) {
        List<Item> content = queryFactory
                .select(item)
                .from(item)
                .leftJoin(item.member, member)
                .leftJoin(item.itemCategory, itemCategory)
                .leftJoin(item.lecture, lecture)
                .fetchJoin()
                .where(classificationEq(cond.getClassification()),
                        itemCategoryEq(cond.getItemCategoryId()),
                        itemTitleContains(cond.getTitle()),
                        lectureNameContains(cond.getLectureName()),
                        priceGoe(cond.getPriceGoe()),
                        priceLoe(cond.getPriceLoe()))
                .offset(pageable.getOffset()) //페이징처리
                .limit(pageable.getPageSize()) // 페이징처리
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(item.count())
                .from(item)
                .where(classificationEq(cond.getClassification()),
                        itemCategoryEq(cond.getItemCategoryId()),
                        itemTitleContains(cond.getTitle()),
                        lectureNameContains(cond.getLectureName()),
                        professorNameContains(cond.getProfessorName()),
                        priceGoe(cond.getPriceGoe()),
                        priceLoe(cond.getPriceLoe()));

        return PageableExecutionUtils.getPage(content, pageable,
                countQuery::fetchOne);
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
