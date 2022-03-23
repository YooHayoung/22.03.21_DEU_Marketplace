package com.deu.marketplace.domain.deal.repository;

import com.deu.marketplace.domain.deal.entity.Deal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DealRepository extends JpaRepository<Deal,Long> {
}
