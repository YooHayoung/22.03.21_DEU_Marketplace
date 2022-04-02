package com.deu.marketplace.domain.itemCategory.service;

import com.deu.marketplace.domain.itemCategory.entity.ItemCategory;

import java.util.List;
import java.util.Optional;

public interface ItemCategoryService {

    List<ItemCategory> getAll();

    Optional<ItemCategory> searchOneByCategoryName(String categoryName);

    Optional<ItemCategory> searchOneById(Long id);

    List<ItemCategory> searchByCategoryName(String categoryName);
}
