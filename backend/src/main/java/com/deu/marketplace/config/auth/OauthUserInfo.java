package com.deu.marketplace.config.auth;

import com.deu.marketplace.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OauthUserInfo {
    private String oauthId;
    private String name;
    private String email;
    private String token;

    @Builder
    public OauthUserInfo(String oauthId, String name, String email, String token) {
        this.oauthId = oauthId;
        this.name = name;
        this.email = email;
        this.token = token;
    }

    public Member toMemberEntity() {
        return Member.ByUserBuilder()
                .oauthId(oauthId)
                .name(name)
                .email(email)
                .token(token)
                .build();
    }

    public void setToken(String token) {
        this.token = token;
    }
}