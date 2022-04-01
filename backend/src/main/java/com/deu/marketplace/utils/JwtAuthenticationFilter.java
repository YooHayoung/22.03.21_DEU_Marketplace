package com.deu.marketplace.utils;

import com.deu.marketplace.config.UserPrincipal;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.security.auth.login.LoginException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

import static org.springframework.http.HttpStatus.PERMANENT_REDIRECT;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = parseAccessToken(request);
        log.info(accessToken);

        if (StringUtils.hasText(accessToken)) {
            try {
                jwtTokenUtil.validateToken(accessToken);
                Long memberId = jwtTokenUtil.getMemberIdFromToken(accessToken);
                log.info("Authenticated member Id : " + memberId);
                UserDetails userDetails = customUserDetailsService.loadUserById(memberId);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(memberId, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (ExpiredJwtException e) {
                log.error("Expired Access Token.");
                throw new JwtException("Expired Access Token.");
            }
        }
        filterChain.doFilter(request, response);
    }

    private String parseAccessToken(HttpServletRequest request) {
        Cookie cookie = CookieUtils.getCookie(request, "accessToken").orElse(null);
        if (cookie != null) {
            String accessToken = cookie.getValue();
            return accessToken;
        }
        return null;
    }
}
