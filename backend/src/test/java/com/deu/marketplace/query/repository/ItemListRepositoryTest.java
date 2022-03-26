package com.deu.marketplace.query.repository;

import com.deu.marketplace.common.ItemSearchCond;
import com.deu.marketplace.domain.item.entity.Classification;
import com.deu.marketplace.query.dto.BuyItemDto;
import com.deu.marketplace.query.dto.SellItemDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
class ItemListRepositoryTest {

    @Autowired
    ItemViewRepository itemListRepository;

    @Test
    @Transactional(readOnly = true)
    public void 팝니다_목록_테스트() throws Exception {
        ItemSearchCond cond = new ItemSearchCond(Classification.SELL.name(), null, null,
                null, null, null, null);
        Pageable pageable = Pageable.ofSize(100).withPage(0);
        Long memberId = 2L;

        Page<SellItemDto> sellItemPages = itemListRepository.getSellItemPages(cond, pageable, memberId);

        System.out.println("sellItemPages.getTotalElements() = " + sellItemPages.getTotalElements());
        List<SellItemDto> content = sellItemPages.getContent();
        for (SellItemDto sellItemDto : content) {
            System.out.println("----------------------------------");
            System.out.println("itemId : " + sellItemDto.getItemId());
            System.out.println("classification : " + sellItemDto.getClassification());
            System.out.println("itemImgFile : " + sellItemDto.getItemImgFile());
            System.out.println("title : " + sellItemDto.getTitle());
            System.out.println("itemCategoryId : " + sellItemDto.getItemCategoryId());
            System.out.println("itemCategoryName : " + sellItemDto.getItemCategoryName());
            System.out.println("lectureName : " + sellItemDto.getLectureName());
            System.out.println("professorName : " + sellItemDto.getProfessorName());
            System.out.println("price : " + sellItemDto.getPrice());
            System.out.println("itemDealId : " + sellItemDto.getItemDealId());
            System.out.println("dealState : " + sellItemDto.getDealState());
            System.out.println("wishedMemberId : " + sellItemDto.getWishedMemberId());
            System.out.println("lastModifiedDate : " + sellItemDto.getLastModifiedDate());
        }
    }

    @Test
    @Transactional(readOnly = true)
    public void 삽니다_목록_테스트() throws Exception {
        ItemSearchCond cond = new ItemSearchCond(Classification.BUY.name(), null, null,
                null, null, null, null);
        Pageable pageable = Pageable.ofSize(100).withPage(0);
        Long memberId = 2L;

        Page<BuyItemDto> buyItemPages = itemListRepository.getBuyItemPages(cond, pageable, memberId);

        System.out.println("sellItemPages.getTotalElements() = " + buyItemPages.getTotalElements());
        List<BuyItemDto> content = buyItemPages.getContent();
        for (BuyItemDto buyItemDto : content) {
            System.out.println("----------------------------------");
            System.out.println("itemId : " + buyItemDto.getItemId());
            System.out.println("classification : " + buyItemDto.getClassification());
            System.out.println("itemImgFile : " + buyItemDto.getItemImgFile());
            System.out.println("title : " + buyItemDto.getTitle());
            System.out.println("price : " + buyItemDto.getPrice());
            System.out.println("itemDealId : " + buyItemDto.getItemDealId());
            System.out.println("dealState : " + buyItemDto.getDealState());
            System.out.println("wishedMemberId : " + buyItemDto.getWishedMemberId());
            System.out.println("lastModifiedDate : " + buyItemDto.getLastModifiedDate());
        }
    }
}