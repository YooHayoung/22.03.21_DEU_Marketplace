package com.deu.marketplace.domain.itemImg.service.impl;

import com.deu.marketplace.domain.itemImg.entity.ItemImg;
import com.deu.marketplace.domain.itemImg.repository.ItemImgRepository;
import com.deu.marketplace.domain.itemImg.service.ItemImgService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemImgServiceImpl implements ItemImgService {

    private final ItemImgRepository itemImgRepository;

    @Override
    public List<ItemImg> saveAll(List<ItemImg> itemImgs) {
        return itemImgRepository.saveAll(itemImgs);
    }

    @Override
    public List<ItemImg> getAllByItemId(Long itemId) {
        return itemImgRepository.findAllByItemId(itemId);
    }
}
