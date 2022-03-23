package com.deu.marketplace.domain.itemCategory.service.impl;

import com.deu.marketplace.domain.itemCategory.entity.ItemCategory;
import com.deu.marketplace.domain.itemCategory.repository.ItemCategoryRepository;
import com.deu.marketplace.domain.itemCategory.service.ItemCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemCategoryServiceImpl implements ItemCategoryService {

    private final ItemCategoryRepository itemCategoryRepository;

    @Override
    public List<ItemCategory> searchByCategoryName(String categoryName) {
        return itemCategoryRepository.findByCategoryNameContains(categoryName);
    }

    @Override
    public List<ItemCategory> getAll() {
        return itemCategoryRepository.findAll();
    }

    @Override
    public Optional<ItemCategory> searchOneByCategoryName(String categoryName) {
        return itemCategoryRepository.findByCategoryName(categoryName);
    }

    @Override
    public Optional<ItemCategory> searchOneById(Long id) {
        return itemCategoryRepository.findById(id);
    }
}
