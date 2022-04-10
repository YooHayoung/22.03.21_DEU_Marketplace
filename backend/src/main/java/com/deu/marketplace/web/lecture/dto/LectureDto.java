package com.deu.marketplace.web.lecture.dto;

import com.deu.marketplace.domain.lecture.entity.Lecture;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LectureDto {
    private Long id;
    private String lectureName;
    private String professorName;

    @Builder
    public LectureDto(Lecture lecture) {
        this.id = lecture.getId();
        this.lectureName = lecture.getLectureName();
        this.professorName = lecture.getProfessorName();
    }

    public Lecture toEntity() {
        return Lecture.dtoToEntityBuilder()
                .id(id)
                .lectureName(lectureName)
                .professorName(professorName)
                .build();
    }
}
