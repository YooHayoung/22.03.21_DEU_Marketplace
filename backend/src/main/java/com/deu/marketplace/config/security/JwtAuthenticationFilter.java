package com.deu.marketplace.config.security;

import com.deu.marketplace.config.security.service.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private RequestMatcher requestMatcher = new AntPathRequestMatcher("/api/**");

    @Resource
    private Environment env;

    private final CookieUtils cookieUtils;

    private final JwtProvider jwtProvider;

    private AdminConfig adminConfig;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (requestMatcher.matches(request)) {

            String jwt = cookieUtils.getcookieValue(request, env.getProperty("jwt.token-name"));

            if (logger.isDebugEnabled()) {
                logger.debug(jwt);
            }

            if (jwt.isEmpty()) {

                if (logger.isDebugEnabled()) {
                    logger.debug("JWT is empty");
                }

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            } else {
                if (!jwtProvider.isValidateToken(jwt)) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("JWT is invalid");
                    }
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                }

                if (jwtProvider.isTokenExpired(jwt)) {
                    if (logger.isDebugEnabled()) {
                        logger.debug("JWT is expired");
                    }
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                }

                Map<String, Object> attributes = jwtProvider.getBodyFromToken(jwt);

                if (logger.isDebugEnabled()) {
                    logger.debug("JWT::" + attributes);
                }

                // JWT로부터 사용자 정보를 추출하여 인증 정보를 만든 후 SecurityContext에 넣는다.
                // 결과적으로 처음 인증 공급자로부터 받은 정보를 JWT에 넣었고 쿠키를 통해 다시 받으면
                // 그것을 OAuth2User로 다시 복원해서 시큐리티의 인증정보에 넣어야 시큐리티의 필터들을 통과할 수 있다.

                // JWT를 만들 때 사용자 고유 ID로 삼았던 필드명과 맞춘다.
                String userNameAttributeName = "sub";

                // TODO
                // 권한은 JWT에 저장하지 않기로 했는데?
                List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

                // TODO 일반 사용자 권한은 ROLE_USER로 통일하고
                // 관리자 권한은 관리자 sub를 별도로 저장해놓고 해당 sub가 접속하면 관리자 권한을 주도록 해보자.

                List<String> admins = adminConfig.getAdmins();
                if (logger.isDebugEnabled()) {
                    admins.forEach(admin -> logger.debug(admin));
                }

                boolean isAdmin = admins.stream().anyMatch(a -> a.equals(attributes.get("sub")));

                if (isAdmin) {
                    authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                } else {
                    authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                }

                OAuth2User userDetails = new DefaultOAuth2User(authorities, attributes, userNameAttributeName);
                OAuth2AuthenticationToken authentication = new OAuth2AuthenticationToken(userDetails, authorities, userNameAttributeName);

                authentication.setDetails(userDetails);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        }

        filterChain.doFilter(request, response);
    }
}
