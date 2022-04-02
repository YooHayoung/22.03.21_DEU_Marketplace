package com.deu.marketplace.domain.postCategory.service.impl;

import com.deu.marketplace.domain.postCategory.entity.PostCategory;
import com.deu.marketplace.domain.postCategory.repository.PostCategoryRepository;
import com.deu.marketplace.domain.postCategory.service.PostCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostCategoryServiceImpl implements PostCategoryService {

    private final PostCategoryRepository postCategoryRepository;

    @Override
    public List<PostCategory> getAll() {
        return postCategoryRepository.findAll();
    }

    @Override
    public Optional<PostCategory> searchOneById(Long id) {
        return postCategoryRepository.findById(id);
    }

    @Override
    public Optional<PostCategory> searchOneByCategoryName(String categoryName) {
        return postCategoryRepository.findByCategoryName(categoryName);
    }

    @Override
    public List<PostCategory> searchByCategoryName(String categoryName) {
        return postCategoryRepository.findByCategoryNameContains(categoryName);
    }
}
