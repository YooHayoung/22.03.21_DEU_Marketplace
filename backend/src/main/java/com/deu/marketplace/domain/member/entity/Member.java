package com.deu.marketplace.domain.member.entity;

import com.deu.marketplace.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.*;

// TODO 회원 도메인 수정
@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String oauthId;

    private String email;

    private String name;

    private String profileImg;

    @Column(nullable = false)
    private String nickname;

    private String univEmail;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    private Boolean deleted;

    @Builder(builderClassName = "ByMemberBuilder", builderMethodName = "ByMemberBuilder")
    public Member(String oauthId, String name, String email, String profileImg, String univEmail) {
        this.oauthId = oauthId;
        this.name = name;
        this.email = email;
        this.profileImg = profileImg;
        this.univEmail = univEmail;
        this.nickname = createRandomNickname();
        this.role = Role.MEMBER;
        this.deleted = false;
    }

    private String createRandomNickname() {
        int length = 10;
        String defaultFrontNickName = "회원";
        String theAlphaNumericS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder stringBuilder = new StringBuilder(length);

        for (int i = 0; i<length; i++) {
            int stringIndex = (int)(theAlphaNumericS.length()*Math.random());
            stringBuilder.append(theAlphaNumericS.charAt(stringIndex));
        }
        return defaultFrontNickName + stringBuilder.toString();
    }

    public Member updateInfo(String name, String email) {
        this.name = name;
        this.univEmail = email;
        return this;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRoleKey() {
        return role.getKey();
    }
}
