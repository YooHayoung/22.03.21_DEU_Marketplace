package com.deu.marketplace.web.lecture.dto;

import com.deu.marketplace.domain.lecture.entity.Lecture;
import lombok.Getter;

@Getter
public class LecutreSearchResponseDto {

    private Long id;
    private String lectureName;
    private String professorName;

    public LecutreSearchResponseDto(Long id, String lectureName, String professorName) {
        this.id = id;
        this.lectureName = lectureName;
        this.professorName = professorName;
    }

    public LecutreSearchResponseDto(Lecture lecture) {
        this.id = lecture.getId();
        this.lectureName = lecture.getLectureName();
        this.professorName = lecture.getProfessorName();
    }
}
