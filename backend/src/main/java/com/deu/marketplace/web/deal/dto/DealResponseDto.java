package com.deu.marketplace.web.deal.dto;

import com.deu.marketplace.domain.deal.entity.Deal;
import com.deu.marketplace.domain.item.entity.Classification;
import com.deu.marketplace.web.item.dto.MemberShortInfoDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DealResponseDto {

    private Long dealId;
    private MemberShortInfoDto sellerInfo;
    private MemberShortInfoDto buyerInfo;
    private String appointmentDate;
    private String meetingPlace;
    private String dealState;

    @Builder
    public DealResponseDto(Deal deal) {
        if (deal.getItem().getClassification() == Classification.SELL) {
            this.dealId = deal.getId();
            this.sellerInfo = MemberShortInfoDto.builder().member(deal.getItem().getMember()).build();
            this.buyerInfo = MemberShortInfoDto.builder().member(deal.getTargetMember()).build();
            this.appointmentDate =
                    deal.getAppointmentDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            this.meetingPlace = deal.getMeetingPlace();
            this.dealState = deal.getDealState().name();
        } else {
            this.dealId = deal.getId();
            this.sellerInfo = MemberShortInfoDto.builder().member(deal.getTargetMember()).build();
            this.buyerInfo = MemberShortInfoDto.builder().member(deal.getItem().getMember()).build();
            this.appointmentDate =
                    deal.getAppointmentDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            this.meetingPlace = deal.getMeetingPlace();
            this.dealState = deal.getDealState().name();
        }
    }
}
