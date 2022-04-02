package com.deu.marketplace.domain.lecture.service.impl;

import com.deu.marketplace.domain.lecture.entity.Lecture;
import com.deu.marketplace.domain.lecture.repository.LectureRepository;
import com.deu.marketplace.domain.lecture.service.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LectureServiceImpl implements LectureService {

    private final LectureRepository lectureRepository;

    @Override
    public List<Lecture> getAll() {
        return lectureRepository.findAll();
    }

    @Override
    public Optional<Lecture> getOneById(Long id) {
        return lectureRepository.findById(id);
    }

    @Override
    public Optional<Lecture> getOneByLectureName(String lectureName) {
        return lectureRepository.findByLectureName(lectureName);
    }

    @Override
    public List<Lecture> getLecturesByLectureName(String lectureName) {
        return lectureRepository.findByLectureNameContains(lectureName);
    }

    @Override
    public Page<Lecture> getAllByPage(Pageable pageable) {
        return lectureRepository.findAll(pageable);
    }
}
