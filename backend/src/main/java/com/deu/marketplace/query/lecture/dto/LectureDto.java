package com.deu.marketplace.query.lecture.dto;

import com.deu.marketplace.domain.lecture.entity.Lecture;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LectureDto {

    private Long lectureId;
    private String lectureName;
    private String professorName;

    @QueryProjection
    public LectureDto(Lecture lecture) {
        this.lectureId = lecture.getId();
        this.lectureName = lecture.getLectureName();
        this.professorName = lecture.getProfessorName();
    }
}
