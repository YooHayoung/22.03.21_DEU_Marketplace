package com.deu.marketplace.domain.itemCategory.service;

import com.deu.marketplace.domain.itemCategory.entity.ItemCategory;
import com.deu.marketplace.domain.itemCategory.repository.ItemCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemCategoryService {

    private final ItemCategoryRepository itemCategoryRepository;

    public List<ItemCategory> findAll() {
        return itemCategoryRepository.findAll();
    }

    public Optional<ItemCategory> findById(Long itemCategoryId) {
        return itemCategoryRepository.findById(itemCategoryId);
    }

    public List<ItemCategory> findByCategoryName(String categoryName) {
        return itemCategoryRepository.findByCategoryNameContains(categoryName);
    }
}
