package com.deu.marketplace.config;

import com.deu.marketplace.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

// Member를 생성자로 받아서 Spring Security에 전달.
// 인증된 Spring Security 주체를 나타냄.
@Getter
public class UserPrincipal implements OAuth2User {
    private Long memberId;
    private String oauthId;
    private String name;
    private String email;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    @Builder(builderClassName = "byNoAttributesBuilder", builderMethodName = "byNoAttributesBuilder")
    public UserPrincipal(Long memberId, String oauthId, String name, String email,
                         Collection<? extends GrantedAuthority> authorities) {
        this.memberId = memberId;
        this.oauthId = oauthId;
        this.name = name;
        this.email = email;
        this.authorities = authorities;
    }

    @Builder(builderClassName = "byMemberBuilder", builderMethodName = "byMemberBuilder")
    public UserPrincipal(Member member, Map<String, Object> attributes) {
        this.memberId = member.getId();
        this.oauthId = member.getOauthId();
        this.name = member.getName();
        this.email = member.getEmail();
        this.authorities = Collections.singleton(new SimpleGrantedAuthority(member.getRoleKey()));
        this.attributes = attributes;
    }
}