package com.deu.marketplace.domain.deal.repository;

import com.deu.marketplace.domain.deal.entity.Deal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DealRepository extends JpaRepository<Deal,Long> {
    Optional<Deal> findByItemId(Long itemId);
}
