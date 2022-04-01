package com.deu.marketplace.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.deu.marketplace.domain.member.entity.Member;
import com.deu.marketplace.domain.member.repository.MemberRepository;
import com.deu.marketplace.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

	private final MemberRepository memberRepository;
	private final JwtTokenUtil jwtTokenUtil;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		log.info("OAuth : loadUser");
//		String resourceServerUri = userRequest.getClientRegistration()
//				.getProviderDetails().getUserInfoEndpoint().getUri();
//		String accessToken = userRequest.getAccessToken().getTokenValue();
//
//		System.out.println("accessToken = " + accessToken);
//		System.out.println("resourceServerUri = " + resourceServerUri);

		OAuth2UserService delegate = new DefaultOAuth2UserService();
		OAuth2User oAuth2User = delegate.loadUser(userRequest);

		String registrationId = userRequest.getClientRegistration()
				.getRegistrationId(); // OAuth 서비스 이름(ex. github, naver, google)
		String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
				.getUserInfoEndpoint().getUserNameAttributeName(); // OAuth 로그인 시 키(pk) 값
		Map<String, Object> attributes = oAuth2User.getAttributes(); // OAuth 서비스의 유저 정보들
		OauthUserInfo userInfo = OAuthAttributes.extract(registrationId, attributes);
		// registrationId에 따라 유저 정보를 통해 공통된 UserProfile 객체로 만들어 줌

		Member member = saveOrUpdate(userInfo);
		return UserPrincipal.byMemberBuilder()
				.member(member)
				.attributes(attributes)
				.build();
	}

	// refreshToken 재발급
	private Member saveOrUpdate(OauthUserInfo userInfo) {
		Member member = memberRepository.findByOauthId(userInfo.getOauthId())
				.map(m -> m.updateInfo(userInfo.getName(), userInfo.getEmail(), jwtTokenUtil.createRefreshToken()))
				.orElse(userInfo.toMemberEntity());
		return memberRepository.save(member);
	}

}
