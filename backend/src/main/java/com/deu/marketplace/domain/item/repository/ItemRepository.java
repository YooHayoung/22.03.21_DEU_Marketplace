package com.deu.marketplace.domain.item.repository;

import com.deu.marketplace.domain.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryCustom {

    @Query("select i " +
            "from Item i " +
            "left join fetch i.itemCategory ic " +
            "left join fetch i.lecture l " +
            "left join fetch i.member m " +
            "where i.id = :itemId")
    Optional<Item> findItemFetchJoinByMemberId(@Param("itemId") Long itemId);
}
