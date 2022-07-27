package com.deu.marketplace.common.search;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class LectureSearchCond {
    private String lectureName;
    private String professorName;

    public LectureSearchCond(String lectureName, String professorName) {
        this.lectureName = lectureName;
        this.professorName = professorName;
    }
}
