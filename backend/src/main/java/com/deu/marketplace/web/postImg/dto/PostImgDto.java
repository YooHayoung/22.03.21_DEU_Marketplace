package com.deu.marketplace.web.postImg.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostImgDto {
    private Long imgId;
    private String img;
    private int seq;
}
