package com.deu.marketplace.domain.item.service.impl;

import com.deu.marketplace.common.ItemSearchCond;
import com.deu.marketplace.domain.item.entity.Item;
import com.deu.marketplace.domain.item.repository.ItemRepository;
import com.deu.marketplace.domain.item.service.ItemService;
import com.deu.marketplace.domain.itemImg.entity.ItemImg;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.ValidationException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    public Optional<Item> getOneItemInfoById(Long itemId) {
        return itemRepository.findItemFetchJoinByMemberId(itemId);
    }

    @Override
    @Transactional
    public Item updateItem(Long itemId, Item item, Long memberId) throws ValidationException {
        Item findItem = itemRepository.findItemFetchJoinByMemberId(itemId).orElseThrow();
        findItem.validWriterIdAndMemberId(memberId);
        findItem.clearItemImgs();
        item.getItemImgs().stream().map(itemImg -> ItemImg.builder()
                .item(findItem)
                .imgFile(itemImg.getImgFile())
                .imgSeq(itemImg.getImgSeq())
                .build()).collect(Collectors.toList());
        findItem.updateItem(item);
        return findItem;
    }

    @Override
    @Transactional
    public void deleteItem(Long itemId, Long memberId) throws ValidationException {
        Item item = itemRepository.findById(itemId).orElseThrow();
        item.validWriterIdAndMemberId(memberId);
        itemRepository.delete(item);
    }
}
