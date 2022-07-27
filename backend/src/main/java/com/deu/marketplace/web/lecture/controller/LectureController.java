package com.deu.marketplace.web.lecture.controller;

import com.deu.marketplace.common.annotation.QueryStringArg;
import com.deu.marketplace.common.response.ApiResponse;
import com.deu.marketplace.common.search.LectureSearchCond;
import com.deu.marketplace.domain.lecture.service.LectureService;
import com.deu.marketplace.web.lecture.dto.LecutreSearchResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/lecture")
public class LectureController {

    private final LectureService lectureService;

    @GetMapping("/search")
    public ApiResponse<?> search(@QueryStringArg LectureSearchCond searchCond,
                                 @PageableDefault(size = 20, page = 0)
                                 @SortDefault.SortDefaults({
                                         @SortDefault(sort = "lectureName", direction = Sort.Direction.ASC),
                                         @SortDefault(sort = "professorName", direction = Sort.Direction.ASC)
                                 }) Pageable pageable) {

        Page<LecutreSearchResponseDto> responseDtos =
                lectureService.findLectures(searchCond, pageable)
                        .map(LecutreSearchResponseDto::new);

        return ApiResponse.success("result", responseDtos);
    }
}
