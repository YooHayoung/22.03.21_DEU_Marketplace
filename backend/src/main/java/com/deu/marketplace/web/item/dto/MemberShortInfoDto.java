package com.deu.marketplace.web.item.dto;

import com.deu.marketplace.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberShortInfoDto {
    private Long memberId;
    private String nickname;

    @Builder
    public MemberShortInfoDto(Member member) {
        this.memberId = member.getId();
        this.nickname = member.getNickname();
    }
}
