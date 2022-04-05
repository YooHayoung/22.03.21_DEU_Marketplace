package com.deu.marketplace.config.auth;

import com.deu.marketplace.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

// Member를 생성자로 받아서 Spring Security에 전달.
// 인증된 Spring Security 주체를 나타냄.
@Getter
public class UserPrincipal implements OAuth2User, UserDetails {
    private Long memberId;
    private String oauthId;
    private String name;
    private String email;
    private String accessToken;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    @Builder(builderClassName = "byNoAttributesBuilder", builderMethodName = "byNoAttributesBuilder")
    public UserPrincipal(Long memberId, String oauthId, String name, String email, String accessToken,
                         Collection<? extends GrantedAuthority> authorities) {
        this.memberId = memberId;
        this.oauthId = oauthId;
        this.name = name;
        this.email = email;
        this.accessToken = accessToken;
        this.authorities = authorities;
    }

    @Builder(builderClassName = "byMemberBuilder", builderMethodName = "byMemberBuilder")
    public UserPrincipal(Member member, Map<String, Object> attributes, String accessToken) {
        this.memberId = member.getId();
        this.oauthId = member.getOauthId();
        this.name = member.getName();
        this.email = member.getEmail();
        this.accessToken = accessToken;
        this.authorities = Collections.singleton(new SimpleGrantedAuthority(member.getRoleKey()));
        this.attributes = attributes;
    }

    @Builder(builderClassName = "byMemberWithNoAttributesBuilder", builderMethodName = "byMemberWithNoAttributesBuilder")
    public UserPrincipal(Member member, String accessToken) {
        this.memberId = member.getId();
        this.oauthId = member.getOauthId();
        this.name = member.getName();
        this.email = member.getEmail();
        this.accessToken = accessToken;
        this.authorities = Collections.singleton(new SimpleGrantedAuthority(member.getRoleKey()));
    }


    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}