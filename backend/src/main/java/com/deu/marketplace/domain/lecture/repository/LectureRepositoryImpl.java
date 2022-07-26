package com.deu.marketplace.domain.lecture.repository;

import com.deu.marketplace.common.search.LectureSearchCond;
import com.deu.marketplace.domain.lecture.entity.Lecture;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;

import java.util.List;

import static com.deu.marketplace.domain.lecture.entity.QLecture.*;

public class LectureRepositoryImpl implements LectureRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public LectureRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Lecture> searchLectures(LectureSearchCond cond, Pageable pageable) {
        List<Lecture> contents = queryFactory.selectFrom(lecture)
                .where(lectureNameContains(cond.getLectureName()),
                        professorNameContains(cond.getProfessorName()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory.select(lecture.count())
                .from(lecture)
                .where(lectureNameContains(cond.getLectureName()),
                        professorNameContains(cond.getProfessorName()))
                .fetchOne();

        return new PageImpl<>(contents, pageable, total);
    }

    private BooleanExpression lectureNameContains(String lectureName) {
        return StringUtils.isEmpty(lectureName)
                ? null
                : lecture.lectureName.contains(lectureName);
    }

    private BooleanExpression professorNameContains(String professorName) {
        return StringUtils.isEmpty(professorName)
                ? null
                : lecture.professorName.contains(professorName);
    }
}
