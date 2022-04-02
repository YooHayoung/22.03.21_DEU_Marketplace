package com.deu.marketplace.domain.itemImg.service.impl;

import com.deu.marketplace.common.ItemSearchCond;
import com.deu.marketplace.domain.itemImg.entity.ItemImg;
import com.deu.marketplace.domain.itemImg.repository.ItemImgRepository;
import com.deu.marketplace.domain.itemImg.service.ItemImgService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemImgServiceImpl implements ItemImgService {

    private final ItemImgRepository itemImgRepository;

    @Override
    public Page<ItemImg> getFirstItemImgsByItemId(ItemSearchCond cond, Pageable pageable) {
        return itemImgRepository.searchFirstItemImgByItem(cond, pageable);
    }

    @Override
    @Transactional
    public List<ItemImg> saveAll(List<ItemImg> itemImgs) {
        return itemImgRepository.saveAll(itemImgs);
    }

    @Override
    public List<ItemImg> getAllByItemId(Long itemId) {
        return itemImgRepository.findAllByItemId(itemId);
    }
}
