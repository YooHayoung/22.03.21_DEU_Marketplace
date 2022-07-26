package com.deu.marketplace.domain.itemCategory.repository;

import com.deu.marketplace.domain.itemCategory.entity.ItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemCategoryRepository extends JpaRepository<ItemCategory, Long> {
}
