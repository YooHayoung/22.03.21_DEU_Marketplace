package com.deu.marketplace.config.auth;

import com.deu.marketplace.domain.member.entity.Member;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionMember implements Serializable {
    private Long memberId;
    private String name;
    private String email;

    public SessionMember(Member member) {
        this.memberId = member.getId();
        this.name = member.getName();
        this.email = member.getEmail();
    }
}
