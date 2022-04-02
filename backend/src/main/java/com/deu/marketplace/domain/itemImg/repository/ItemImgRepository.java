package com.deu.marketplace.domain.itemImg.repository;

import com.deu.marketplace.domain.itemImg.entity.ItemImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemImgRepository extends JpaRepository<ItemImg, Long>, ItemImgRepositoryCustom {
    List<ItemImg> findAllByItemId(Long itemId);
}
