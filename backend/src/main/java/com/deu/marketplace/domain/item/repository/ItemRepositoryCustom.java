package com.deu.marketplace.domain.item.repository;

import com.deu.marketplace.common.ItemSearchCond;
import com.deu.marketplace.domain.item.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {
    Page<Item> searchItemPage(ItemSearchCond cond, Pageable pageable);
}
