package com.deu.marketplace.domain.member.entity;

import lombok.Getter;

@Getter
public enum Role {
    GUEST("ROLE_GUEST"),
    MEMBER("ROEL_MEMBER");

    private final String key;

    Role(String key) {
        this.key = key;
    }
}
