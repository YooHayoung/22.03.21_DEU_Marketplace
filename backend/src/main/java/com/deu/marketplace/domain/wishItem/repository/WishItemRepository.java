package com.deu.marketplace.domain.wishItem.repository;

import com.deu.marketplace.domain.wishItem.entity.WishItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface WishItemRepository extends JpaRepository<WishItem, Long>, WishItemRepositoryCustom {

    @Query(value = "select wi from WishItem wi where wi.item.id = :itemId and wi.wishedMember.id = :memberId")
    Optional<WishItem> findByInfo(@Param("itemId") Long itemId, @Param("memberId") Long memberId);

}
