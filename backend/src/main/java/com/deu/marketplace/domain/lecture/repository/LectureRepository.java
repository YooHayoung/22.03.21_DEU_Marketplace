package com.deu.marketplace.domain.lecture.repository;

import com.deu.marketplace.domain.lecture.entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureRepository extends JpaRepository<Lecture, Long>, LectureRepositoryCustom {
}
