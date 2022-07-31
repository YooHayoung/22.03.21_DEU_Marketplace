package com.deu.marketplace.config.auth;

import com.deu.marketplace.domain.member.entity.Member;
import com.deu.marketplace.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuthUserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        ////////
        log.warn(userRequest.getAccessToken().getTokenValue());
        log.warn(userRequest.getAccessToken().getExpiresAt().toString());
        ///////

        String registrationId = userRequest.getClientRegistration()
                .getRegistrationId(); // OAuth 서비스 이름(ex. github, naver, google)
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName(); // OAuth 로그인 시 키(pk) 값
        Map<String, Object> attributes = oAuth2User.getAttributes(); // OAuth 서비스의 유저 정보들
        OauthUserInfo userInfo = OAuthAttributes.extract(registrationId, attributes);
        userInfo.setToken(userRequest.getAccessToken().getTokenValue());
        // registrationId에 따라 유저 정보를 통해 공통된 UserProfile 객체로 만들어 줌

        Member member = saveOrUpdate(userInfo);

        return UserPrincipal.byMemberBuilder()
                .member(member)
                .accessToken(userRequest.getAccessToken().getTokenValue())
                .attributes(attributes)
                .build();
    }

    private Member saveOrUpdate(OauthUserInfo userInfo) {
        Member member = memberRepository.findByOauthId(userInfo.getOauthId())
                .map(m -> m.updateInfo(userInfo.getName(), userInfo.getEmail(), userInfo.getToken()))
                .orElse(userInfo.toMemberEntity());
        return memberRepository.save(member);
    }
}
