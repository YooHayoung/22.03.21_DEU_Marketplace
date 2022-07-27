package com.deu.marketplace.domain.itemCategory.repository;

import com.deu.marketplace.domain.itemCategory.entity.ItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemCategoryRepository extends JpaRepository<ItemCategory, Long> {
    Optional<ItemCategory> findByCategoryName(String categoryName);

    List<ItemCategory> findByCategoryNameContains(String categoryName);
}
