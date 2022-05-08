package com.deu.marketplace.web.itemImg.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemImgDto {
    private Long imgId;
    private String img;
    private int seq;
}
