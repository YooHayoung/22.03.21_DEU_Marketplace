package com.deu.marketplace.web.itemCategory.controller;

import com.deu.marketplace.common.response.ApiResponse;
import com.deu.marketplace.domain.itemCategory.service.ItemCategoryService;
import com.deu.marketplace.web.itemCategory.dto.ItemCategoryResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/itemCategory")
public class ItemCategoryController {

    private final ItemCategoryService itemCategoryService;

    @GetMapping("/list")
    public ApiResponse<?> getItemCategoryList() {
        List<ItemCategoryResponseDto> categories = itemCategoryService.findAll().stream()
                .map(itemCategory -> new ItemCategoryResponseDto(itemCategory.getId(), itemCategory.getCategoryName()))
                .collect(Collectors.toList());

        return ApiResponse.success("result", categories);
    }
}
