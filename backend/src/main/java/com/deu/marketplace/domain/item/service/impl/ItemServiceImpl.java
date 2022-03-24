package com.deu.marketplace.domain.item.service.impl;

import com.deu.marketplace.common.ItemSearchCond;
import com.deu.marketplace.domain.item.entity.Item;
import com.deu.marketplace.domain.item.repository.ItemRepository;
import com.deu.marketplace.domain.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    @Override
    @Transactional
    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public Page<Item> searchItemPage(ItemSearchCond cond, Pageable pageable) {
        return itemRepository.searchItemPage(cond, pageable);
    }

    @Override
    public Optional<Item> getOneItemById(Long id) {
        return itemRepository.findById(id);
    }
}
