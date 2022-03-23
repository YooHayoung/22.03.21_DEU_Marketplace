package com.deu.marketplace.domain.member.entity;

import com.deu.marketplace.domain.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import javax.persistence.*;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private Boolean certification;

    @Builder(builderClassName = "ByDefaultNicknameBuilder", builderMethodName = "ByDefaultNicknameBuilder")
    public Member(String name, String email) {
        Assert.notNull(name, "name must not be null");
        Assert.notNull(email, "email must not be null");

        this.name = name;
        this.email = email;
        this.nickname = createRandomNickname();
        this.certification = false;
    }

    public String createRandomNickname() {
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

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void certifyByUnivEmail() {
        if(!this.isCertified()) {
            certification = !certification;
        }
    }

    public Boolean isCertified() {
        return this.certification;
    }

}
