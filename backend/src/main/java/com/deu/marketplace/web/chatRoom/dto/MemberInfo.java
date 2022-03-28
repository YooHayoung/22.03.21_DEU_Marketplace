package com.deu.marketplace.web.chatRoom.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberInfo {
    private Long memberId;
    private String nickname;

    @Builder
    public MemberInfo(Long memberId, String nickname) {
        this.memberId = memberId;
        this.nickname = nickname;
    }
}
