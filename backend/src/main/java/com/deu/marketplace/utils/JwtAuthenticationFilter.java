package com.deu.marketplace.utils;

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
import java.util.Arrays;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = parseBeareToken(request);
            log.info(jwt);
            if (StringUtils.hasText(jwt) && jwtTokenUtil.validateToken(jwt)) {
                Long memberId = jwtTokenUtil.getMemberIdFromToken(jwt);
                log.info("Authenticated member Id : " + memberId);
                UserDetails userDetails = customUserDetailsService.loadUserById(memberId);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(memberId, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex) {
            log.error("Could not set user authentication in security context", ex);
        }
        filterChain.doFilter(request, response);
    }

    private String parseBeareToken(HttpServletRequest request) {
        Cookie cookie = CookieUtils.getCookie(request, "token").orElse(null);
        if (cookie != null) {
            String bearerToken = cookie.getValue();
//            if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
//                return bearerToken.substring(7);
//            }
            return bearerToken;
        }
        return null;
    }
}
