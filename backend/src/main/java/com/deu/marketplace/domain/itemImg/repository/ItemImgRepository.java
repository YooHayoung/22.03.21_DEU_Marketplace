package com.deu.marketplace.domain.itemImg.repository;

import com.deu.marketplace.domain.itemImg.entity.ItemImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemImgRepository extends JpaRepository<ItemImg, Long> {
    List<ItemImg> findAllByItemId(Long itemId);
}
