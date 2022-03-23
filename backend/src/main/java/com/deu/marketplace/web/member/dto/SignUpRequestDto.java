package com.deu.marketplace.web.member.dto;

import com.deu.marketplace.domain.member.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignUpRequestDto {

    private String name;
    private String email;

    @Builder
    public SignUpRequestDto(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Member toEntity() {
        return Member.ByDefaultNicknameBuilder()
                .name(name)
                .email(email)
                .build();
    }
}
