package com.deu.marketplace.web.deal.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DealUpdateRequestDto {
    private String appointmentDate;
    private String meetingPlace;

    public DealUpdateRequestDto(String appointmentDate, String meetingPlace) {
        this.appointmentDate = appointmentDate;
        this.meetingPlace = meetingPlace;
    }
}
