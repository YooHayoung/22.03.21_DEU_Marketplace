package com.deu.marketplace.domain.lecture.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.*;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Lecture {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_id")
    private Long id;

    @Column(nullable = false)
    private String lectureName;

    @Column(nullable = false)
    private String professorName;

    @Builder
    public Lecture(String lectureName, String professorName) {
        Assert.notNull(lectureName, "lectureName must not be null");
        Assert.notNull(professorName, "professorName must not be null");

        this.lectureName = lectureName;
        this.professorName = professorName;
    }

    @Builder(builderClassName = "dtoToEntityBuilder", builderMethodName = "dtoToEntityBuilder")
    public Lecture(Long id, String lectureName, String professorName) {
        this.id = id;
        this.lectureName = lectureName;
        this.professorName = professorName;
    }
}
