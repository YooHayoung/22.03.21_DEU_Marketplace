package com.deu.marketplace.web.lecture.controller;

import com.deu.marketplace.domain.lecture.service.LectureService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/lecture")
public class LectureController {

    private final LectureService lectureService;

    @GetMapping
    public ResponseEntity<?> getAllLectures() {
        return ResponseEntity.ok().body(lectureService.getAll());
    }

    @GetMapping("/paging")
    public ResponseEntity<?> getAllLecturesByPage(
            @PageableDefault(size = 50, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok().body(lectureService.getAllByPage(pageable));
    }
}
