package com.deu.marketplace.domain.lecture.repository;

import com.deu.marketplace.common.search.LectureSearchCond;
import com.deu.marketplace.domain.lecture.entity.Lecture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LectureRepositoryCustom {

    Page<Lecture> searchLectures(LectureSearchCond cond, Pageable pageable);
}
