package com.deu.marketplace.domain.itemImg.service;

import com.deu.marketplace.domain.itemImg.entity.ItemImg;

import java.util.List;

public interface ItemImgService {

    List<ItemImg> saveAll(List<ItemImg> itemImgs);

    List<ItemImg> getAllByItemId(Long itemId);
}
