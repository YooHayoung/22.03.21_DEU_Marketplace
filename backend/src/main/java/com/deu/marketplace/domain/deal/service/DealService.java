package com.deu.marketplace.domain.deal.service;

import com.deu.marketplace.domain.deal.entity.Deal;

import java.util.Optional;

public interface DealService {
    Optional<Deal> getOneByItemId(Long itemId);
}
