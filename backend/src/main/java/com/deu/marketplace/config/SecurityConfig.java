package com.deu.marketplace.config;

import com.deu.marketplace.utils.HttpCookieOAuth2AuthorizationRequestRepository;
import com.deu.marketplace.utils.JwtAuthenticationFilter;
import com.deu.marketplace.utils.OAuth2AuthenticationFailureHandler;
import com.deu.marketplace.utils.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.filters.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(
		securedEnabled = true,
		jsr250Enabled = true,
		prePostEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final CustomOAuth2UserService customOAuth2UserService;
	private final OAuth2SuccessHandler oAuth2SuccessHandler;
	private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
//	private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;
	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	@Bean
	public HttpCookieOAuth2AuthorizationRequestRepository cookieOAuth2AuthorizationRequestRepository() {
		return new HttpCookieOAuth2AuthorizationRequestRepository();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		 http
				 .cors()
				 	.configurationSource(corsConfigurationSource())
				 	.and()
				 .csrf()
				 	.disable()
				 .httpBasic()
				 	.disable()
				 .sessionManagement()
				 	.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				 	.and()
				 .authorizeRequests()
				 	.antMatchers("/oauth/**").permitAll()
				 	.anyRequest().authenticated()
				 	.and()
//				 .headers().frameOptions().disable()
				 .oauth2Login()
					 .authorizationEndpoint()
						.authorizationRequestRepository(cookieOAuth2AuthorizationRequestRepository())
						.baseUri("/oauth/authorization") // front -> server 인증 요청 uri
					 .and()
					 .redirectionEndpoint()
						.baseUri("/oauth/callback/*") // callback uri
					 .and()
					 .userInfoEndpoint()
						 .userService(customOAuth2UserService)
						 .and()
						 .successHandler(oAuth2SuccessHandler)
						 .failureHandler((AuthenticationFailureHandler) oAuth2AuthenticationFailureHandler);
		 http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.addAllowedOriginPattern("*");
		configuration.addAllowedMethod("*");
		configuration.addAllowedHeader("*");
		configuration.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

}
