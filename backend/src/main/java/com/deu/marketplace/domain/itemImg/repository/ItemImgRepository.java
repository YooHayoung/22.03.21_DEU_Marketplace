package com.deu.marketplace.domain.itemImg.repository;

import com.deu.marketplace.domain.itemImg.entity.ItemImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemImgRepository extends JpaRepository<ItemImg, Long>, ItemImgRepositoryCustom {
    List<ItemImg> findAllByItemIdOrderByImgSeqAsc(Long itemId);

    @Query("select img from ItemImg img where img.id in :imgIdList")
    List<ItemImg> findByIdIn(List<Long> imgIdList);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("delete from ItemImg img where img.id in :imgIdList")
    void deleteByIdInBatch(List<Long> imgIdList);


}
