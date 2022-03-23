package com.deu.marketplace.domain.postCategory.service;

import com.deu.marketplace.domain.postCategory.entity.PostCategory;

import java.util.List;
import java.util.Optional;

public interface PostCategoryService {
    List<PostCategory> getAll();

    Optional<PostCategory> searchOneById(Long id);

    Optional<PostCategory> searchOneByCategoryName(String categoryName);

    List<PostCategory> searchByCategoryName(String categoryName);
}
