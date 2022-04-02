package com.deu.marketplace.domain.deal.service.impl;

import com.deu.marketplace.domain.deal.entity.Deal;
import com.deu.marketplace.domain.deal.repository.DealRepository;
import com.deu.marketplace.domain.deal.service.DealService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DealServiceImpl implements DealService {

    private final DealRepository dealRepository;

    @Override
    public Optional<Deal> getOneByItemId(Long itemId) {
        return dealRepository.findByItemId(itemId);
    }
}
