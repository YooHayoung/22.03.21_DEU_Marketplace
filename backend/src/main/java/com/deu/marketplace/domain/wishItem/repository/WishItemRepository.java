package com.deu.marketplace.domain.wishItem.repository;

import com.deu.marketplace.domain.wishItem.entity.WishItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishItemRepository extends JpaRepository<WishItem, Long> {
}
