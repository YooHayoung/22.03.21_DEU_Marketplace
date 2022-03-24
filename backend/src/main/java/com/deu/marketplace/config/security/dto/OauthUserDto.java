package com.deu.marketplace.config.security.dto;

import com.deu.marketplace.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OauthUserDto {
    private String oauthId;
    private String name;
    private String email;

    @Builder
    public OauthUserDto(String oauthId, String name, String email) {
        this.oauthId = oauthId;
        this.name = name;
        this.email = email;
    }

    public Member toMemberEntity() {
        return Member.ByUserBuilder()
                .oauthId(oauthId)
                .name(name)
                .email(email)
                .build();
    }
}
