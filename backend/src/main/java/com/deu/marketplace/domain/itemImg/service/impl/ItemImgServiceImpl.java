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
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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
        return itemImgRepository.findAllByItemIdOrderByImgSeqAsc(itemId);
    }

    @Override
    public List<ItemImg> getByImgIdList(List<Long> imgIdList) {
        return itemImgRepository.findByIdIn(imgIdList);
    }

    @Override
    @Transactional
    public void deleteByImgIdList(List<ItemImg> imgList) {
//        itemImgRepository.deleteByIdInBatch(imgIdList);
        itemImgRepository.deleteInBatch(imgList);
    }

    @Override
    @Transactional
    public List<ItemImg> updateImgSeq(List<ItemImg> itemImgs) {
        AtomicInteger index = new AtomicInteger();
        List<ItemImg> updatedItemImgs = itemImgs.stream().map(img -> {
            img.updateImgSeq(index.getAndIncrement() + 1);
            return img;
        }).collect(Collectors.toList());
//        for (ItemImg itemImg : itemImgs) {
//            itemImg.updateImgSeq(index.getAndIncrement()+1);
//        }
        return updatedItemImgs;
    }

    @Override
    @Transactional
    public void deleteAllByItemId(Long itemId) {
        List<ItemImg> findItemImgs = itemImgRepository.findAllByItemIdOrderByImgSeqAsc(itemId);
        itemImgRepository.deleteInBatch(findItemImgs);
    }
}
