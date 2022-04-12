package com.deu.marketplace.web.deal.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DealStateUpdateRequestDto {
    private String dealState;

    public DealStateUpdateRequestDto(String dealState) {
        this.dealState = dealState;
    }
}
