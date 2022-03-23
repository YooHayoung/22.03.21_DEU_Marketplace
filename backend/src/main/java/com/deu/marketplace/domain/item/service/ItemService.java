package com.deu.marketplace.domain.item.service;

import com.deu.marketplace.common.ItemSearchCond;
import com.deu.marketplace.domain.item.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ItemService {
    Item saveItem(Item item);

    Page<Item> searchItemPage(ItemSearchCond cond, Pageable pageable);

    Optional<Item> getOneItemById(Long id);
}
