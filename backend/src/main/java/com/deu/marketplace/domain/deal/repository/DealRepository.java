package com.deu.marketplace.domain.deal.repository;

import com.deu.marketplace.domain.deal.entity.Deal;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DealRepository extends JpaRepository<Deal,Long> {
    @EntityGraph(attributePaths = {"item.member"})
    Optional<Deal> findById(Long id);

    @Query("select d from Deal d where d.item.id = :itemId and d.dealState <> 'CANCEL'")
    Optional<Deal> findByItemId(@Param("itemId") Long itemId);
}
