package com.deu.marketplace.web.item.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ItemWishInfo {
    private long wishCount;
    private boolean myWish;

    @Builder
    public ItemWishInfo(long wishCount, boolean myWish) {
        this.wishCount = wishCount;
        this.myWish = myWish;
    }
}
