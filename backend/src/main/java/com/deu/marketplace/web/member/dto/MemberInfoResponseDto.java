package com.deu.marketplace.web.member.dto;

import com.deu.marketplace.domain.member.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberInfoResponseDto {
    //  정보 조회 (이름, 등록 이메일, 닉네임, 인증 여부, 가입일)
    private Long memberId;
    private String email;
    private String name;
    private String nickname;
    private boolean stuIdCertificated;
    private String createdDate;

    @Builder
    public MemberInfoResponseDto(Member member) {
        this.memberId = member.getId();
        this.email = member.getEmail();
        this.name = member.getName();
        this.nickname = member.getNickname();
        this.stuIdCertificated = member.getCertification();
        this.createdDate =
                member.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
