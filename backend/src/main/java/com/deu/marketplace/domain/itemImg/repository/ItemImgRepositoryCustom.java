package com.deu.marketplace.domain.itemImg.repository;

import com.deu.marketplace.common.ItemSearchCond;
import com.deu.marketplace.domain.itemImg.entity.ItemImg;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemImgRepositoryCustom {
    Page<ItemImg> searchFirstItemImgByItem(ItemSearchCond cond, Pageable pageable);
}
