package com.deu.marketplace.domain.itemImg.service;

import com.deu.marketplace.common.ItemSearchCond;
import com.deu.marketplace.domain.itemImg.entity.ItemImg;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemImgService {

    Page<ItemImg> getFirstItemImgsByItemId(ItemSearchCond cond, Pageable pageable);

    List<ItemImg> saveAll(List<ItemImg> itemImgs);

    List<ItemImg> getAllByItemId(Long itemId);

    List<ItemImg> getByImgIdList(List<Long> imgIdList);

    void deleteByImgIdList(List<ItemImg> imgList);

    List<ItemImg> updateImgSeq(List<ItemImg> itemImgs);

    void deleteAllByItemId(Long itemId);
}
