package com.deu.marketplace.query.lecture.repository;

import com.deu.marketplace.domain.item.entity.Classification;
import com.deu.marketplace.domain.lecture.entity.QLecture;
import com.deu.marketplace.query.lecture.dto.LectureDto;
import com.deu.marketplace.query.lecture.dto.QLectureDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import java.util.List;

import static com.deu.marketplace.domain.item.entity.QItem.item;
import static com.deu.marketplace.domain.lecture.entity.QLecture.lecture;
import static org.apache.logging.log4j.util.Strings.isEmpty;

@Repository
public class LectureInfoRepository {

    private final JPAQueryFactory queryFactory;

    public LectureInfoRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Page<LectureDto> getLectureInfoPages(String lectureName, String professorName, Pageable pageable) {
        List<LectureDto> content = queryFactory
                .select(new QLectureDto(
                        lecture
                ))
                .from(lecture)
                .where(lectureNameContains(lectureName), professorNameContains(professorName))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(lecture.count())
                .from(lecture)
                .where(lectureNameContains(lectureName), professorNameContains(professorName));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression lectureNameContains(String lectureName) {
        return isEmpty(lectureName) ? null : lecture.lectureName.contains(lectureName);
    }

    private BooleanExpression professorNameContains(String professorName) {
        return isEmpty(professorName) ? null : lecture.professorName.contains(professorName);
    }
}
