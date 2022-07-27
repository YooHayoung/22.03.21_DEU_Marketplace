package com.deu.marketplace.domain.lecture.service;

import com.deu.marketplace.common.search.LectureSearchCond;
import com.deu.marketplace.domain.lecture.entity.Lecture;
import com.deu.marketplace.domain.lecture.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LectureService {

    private final LectureRepository lectureRepository;

    public Optional<Lecture> findById(Long lectureId) {
        return lectureRepository.findById(lectureId);
    }

    public Page<Lecture> findLectures(LectureSearchCond cond, Pageable pageable) {
        return lectureRepository.searchLectures(cond, pageable);
    }
}
