package com.deu.marketplace.domain.item.entity;

import lombok.*;

import javax.persistence.Embeddable;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class BookState {

    private String writeState;
    private String surfaceState;
    private int regularPrice;
}
