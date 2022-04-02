package com.deu.marketplace.config.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

@Getter
@RequiredArgsConstructor
public enum OAuthAttributes {
	// OAuth 서비스별 유저 정보 key값 매핑. 추후 확장을 위하여
	NAVER("naver", (attributes) -> {
		// 네이버는 정식 등록되지 않았기 때문에 수동으로 작업 필요.
		Map<String, Object> response = (Map<String, Object>) attributes.get("response");
		return OauthUserInfo.builder()
				.oauthId((String) response.get("id"))
				.name((String) response.get("name"))
				.email((String) response.get("email"))
				.build();
	});

	private final String registrationId;
	private final Function<Map<String, Object>, OauthUserInfo> of;

	public static OauthUserInfo extract(String registrationId, Map<String, Object> attributes) {
		return Arrays.stream(values())
				.filter(provider -> registrationId.equals(provider.registrationId))
				.findFirst()
				.orElseThrow(IllegalArgumentException::new)
				.of.apply(attributes);
	}

}
