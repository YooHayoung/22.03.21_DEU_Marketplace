package com.deu.marketplace.domain.lecture.repository;

import com.deu.marketplace.domain.lecture.entity.Lecture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
    Optional<Lecture> findByLectureName(String lectureName);
    List<Lecture> findByLectureNameContains(String lectureName);
}
