package com.deu.marketplace.domain.deal.service.impl;

import com.deu.marketplace.domain.deal.entity.Deal;
import com.deu.marketplace.domain.deal.repository.DealRepository;
import com.deu.marketplace.domain.deal.service.DealService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.ValidationException;
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

    @Override
    @Transactional
    public Optional<Deal> updateDeal(Long dealId, String meetingPlace, String appointmentDate) {
        Optional<Deal> deal = dealRepository.findById(dealId);
        if (deal.isPresent()) {
            deal.orElseThrow().updateDealInfo(meetingPlace, appointmentDate);
        }
        return deal;
    }

    @Override
    @Transactional
    public Deal saveDeal(Deal deal) {
        return dealRepository.save(deal);
    }

    @Override
    @Transactional
    public Deal dealComplete(Long dealId, Long memberId) throws ValidationException {
        Deal deal = dealRepository.findById(dealId).orElseThrow();
        deal.validMemberId(memberId);
        deal.completeDeal();
        return deal;
    }

    @Override
    @Transactional
    public Deal dealCancel(Long dealId, Long memberId) throws ValidationException {
        Deal deal = dealRepository.findById(dealId).orElseThrow();
        deal.validMemberId(memberId);
        deal.cancelDeal();
        return deal;
    }
}
