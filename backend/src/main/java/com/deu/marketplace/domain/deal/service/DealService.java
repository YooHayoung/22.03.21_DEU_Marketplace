package com.deu.marketplace.domain.deal.service;

import com.deu.marketplace.domain.deal.entity.Deal;

import javax.xml.bind.ValidationException;
import java.util.Optional;

public interface DealService {
    Optional<Deal> getOneByItemId(Long itemId);

    Optional<Deal> updateDeal(Long dealId, String meetingPlace, String appointmentDate);

    Deal saveDeal(Deal deal);

    Deal dealComplete(Long dealId, Long memberId) throws ValidationException;

    Deal dealCancel(Long dealId, Long memberId) throws ValidationException;

}
