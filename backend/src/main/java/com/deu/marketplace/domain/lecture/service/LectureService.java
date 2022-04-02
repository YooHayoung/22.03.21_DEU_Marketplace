package com.deu.marketplace.domain.lecture.service;

import com.deu.marketplace.domain.lecture.entity.Lecture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface LectureService {
    List<Lecture> getAll();

    Optional<Lecture> getOneById(Long id);

    Optional<Lecture> getOneByLectureName(String lectureName);

    List<Lecture> getLecturesByLectureName(String lectureName);

    Page<Lecture> getAllByPage(Pageable pageable);
}
