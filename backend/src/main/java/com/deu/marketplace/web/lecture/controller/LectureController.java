package com.deu.marketplace.web.lecture.controller;

import com.deu.marketplace.common.ApiResponse;
import com.deu.marketplace.domain.lecture.service.LectureService;
import com.deu.marketplace.query.lecture.dto.LectureDto;
import com.deu.marketplace.query.lecture.repository.LectureInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/lecture")
public class LectureController {

    private final LectureService lectureService;
    private final LectureInfoRepository lectureInfoRepository;

    //수정예정
    
//    @GetMapping
//    public ResponseEntity<?> getAllLectures() {
//        return ResponseEntity.ok().body(lectureService.getAll());
//    }
//
//    @GetMapping("/paging")
//    public ResponseEntity<?> getAllLecturesByPage(
//            @PageableDefault(size = 50, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
//        return ResponseEntity.ok().body(lectureService.getAllByPage(pageable));
//    }

    // ****
    @GetMapping("/search")
    public ApiResponse searchLectures(@RequestParam("lectureName") String lectureName,
                                      @RequestParam("professorName") String professorName,
                                      @PageableDefault(size = 20, page = 0)
                                                @SortDefault.SortDefaults({
                                                        @SortDefault(sort = "lectureName", direction = Sort.Direction.ASC),
                                                        @SortDefault(sort = "professorName", direction = Sort.Direction.ASC)
                                                }) Pageable pageable) {
        Page<LectureDto> lectureInfoPages = lectureInfoRepository.getLectureInfoPages(lectureName, professorName, pageable);
        return ApiResponse.success("result", lectureInfoPages);
    }
}
