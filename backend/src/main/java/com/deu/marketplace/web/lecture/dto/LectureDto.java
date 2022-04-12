package com.deu.marketplace.web.lecture.dto;

import com.deu.marketplace.domain.lecture.entity.Lecture;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LectureDto {
    private Long lectureId;
    private String lectureName;
    private String professorName;

    @Builder
    public LectureDto(Lecture lecture) {
        this.lectureId = lecture.getId();
        this.lectureName = lecture.getLectureName();
        this.professorName = lecture.getProfessorName();
    }

    public Lecture toEntity() {
        return Lecture.dtoToEntityBuilder()
                .id(lectureId)
                .lectureName(lectureName)
                .professorName(professorName)
                .build();
    }
}
