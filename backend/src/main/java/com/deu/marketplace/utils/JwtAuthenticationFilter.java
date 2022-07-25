package com.deu.marketplace.utils;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
//@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String accessToken = HeaderUtil.getAccessToken(request);
        log.info(request.getRequestURI());
        if (StringUtils.hasText(accessToken)) {
            try {
                jwtTokenUtil.validate(accessToken);
                Long memberId = jwtTokenUtil.getMemberIdFromToken(accessToken);
                log.info("Authenticated member Id : " + memberId);
                UserDetails userDetails = customUserDetailsService.loadUserById(memberId);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(memberId, accessToken, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (ExpiredJwtException e) {
                log.error("Expired Access Token.");
                throw new JwtException("Expired Access Token.");
            }
        } else if (request.getRequestURI().contains("/ws")) {
            log.info(request.getQueryString());
            String queryString = request.getQueryString();
        }
        else {
            log.warn("No access Token");
            throw new IOException("No access Token");
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
