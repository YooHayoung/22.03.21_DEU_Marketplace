package com.deu.marketplace.utils;

import com.deu.marketplace.domain.member.entity.Member;
import com.deu.marketplace.domain.member.repository.MemberRepository;
import com.deu.marketplace.domain.memberRefreshToken.entity.MemberRefreshToken;
import com.deu.marketplace.domain.memberRefreshToken.repository.MemberRefreshTokenRepository;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtExceptionFilter extends OncePerRequestFilter {

    private final MemberRepository memberRepository;
    private final MemberRefreshTokenRepository memberRefreshTokenRepository;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        }
        catch (IOException ex) {
            log.error(ex.getMessage());
            String refreshToken = CookieUtils.getCookie(request, "refreshToken")
                    .map(Cookie::getValue)
                    .orElse(null);
            log.error(refreshToken);
            if (!jwtTokenUtil.validate(refreshToken)) {
                log.error("validate false");
                setErrorResponse(HttpStatus.UNAUTHORIZED, response, ex);
                return;
            }

            Optional<MemberRefreshToken> memberRefreshToken =
                    memberRefreshTokenRepository.findByRefreshToken(refreshToken);
            if (memberRefreshToken.isEmpty()) {
                log.error("refreshToken is empty");
                setErrorResponse(HttpStatus.UNAUTHORIZED, response, ex);
                return;
            }
            Member member =
                    memberRepository.findById(memberRefreshToken.orElseThrow().getMemberId()).orElseThrow();
            String accessToken = jwtTokenUtil.createAccessToken(member.getId(), member.getRole());


            log.info("setAuthorization");
            response.setStatus(HttpStatus.TEMPORARY_REDIRECT.value());
            response.setContentType("application/json; charset=UTF-8");
            response.setHeader("Authorization", accessToken);
        }
        catch (JwtException ex) {
            log.error(ex.getMessage());
            setErrorResponse(HttpStatus.UNAUTHORIZED, response, ex);
        }
    }

    public void setErrorResponse(HttpStatus status, HttpServletResponse response, Throwable ex) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json; charset=UTF-8");
    }
}
