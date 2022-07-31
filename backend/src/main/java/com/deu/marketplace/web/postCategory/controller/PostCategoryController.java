package com.deu.marketplace.web.postCategory.controller;

import com.deu.marketplace.common.ApiResponse;
import com.deu.marketplace.domain.postCategory.service.PostCategoryService;
import com.deu.marketplace.web.postCategory.dto.PostCategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/postCategory")
public class PostCategoryController {

    private final PostCategoryService postCategoryService;

    @GetMapping
    public ApiResponse getAllItemCategory() {
        List<PostCategoryDto> postCategoryDtos = postCategoryService.getAll()
                .stream().map(PostCategoryDto::new).collect(Collectors.toList());

        return ApiResponse.success("result", postCategoryDtos);
    }
}
