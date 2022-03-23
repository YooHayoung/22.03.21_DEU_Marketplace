package com.deu.marketplace.web.postCategory.controller;

import com.deu.marketplace.domain.postCategory.service.PostCategoryService;
import com.deu.marketplace.web.ResponseDto;
import com.deu.marketplace.web.itemCategory.dto.ItemCategoryDto;
import com.deu.marketplace.web.postCategory.dto.PostCategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/postCategory")
public class PostController {

    private final PostCategoryService postCategoryService;

    @GetMapping
    public ResponseEntity<?> getAllItemCategory() {
        List<PostCategoryDto> postCategoryDtos = postCategoryService.getAll()
                .stream().map(PostCategoryDto::new).collect(Collectors.toList());
        ResponseDto<PostCategoryDto> responseDto =
                ResponseDto.<PostCategoryDto>builder().data(postCategoryDtos).build();

        return ResponseEntity.ok().body(responseDto);
    }
}
