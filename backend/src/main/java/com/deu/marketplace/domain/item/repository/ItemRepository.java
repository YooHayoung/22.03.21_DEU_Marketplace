package com.deu.marketplace.domain.item.repository;

import com.deu.marketplace.domain.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("select i from Item i where i.id = :itemId and i.writer.id = :memberId")
    Optional<Item> findByItemIdAndMemberId(@Param("itemId") Long itemId,@Param("memberId") Long memberId);
}
