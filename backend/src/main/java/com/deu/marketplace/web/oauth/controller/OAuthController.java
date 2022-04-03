package com.deu.marketplace.web.oauth.controller;


import com.deu.marketplace.common.ApiResponse;
import com.deu.marketplace.config.AppProperties;
import com.deu.marketplace.domain.member.entity.Role;
import com.deu.marketplace.domain.memberRefreshToken.entity.MemberRefreshToken;
import com.deu.marketplace.domain.memberRefreshToken.repository.MemberRefreshTokenRepository;
import com.deu.marketplace.utils.CookieUtils;
import com.deu.marketplace.utils.HeaderUtil;
import com.deu.marketplace.utils.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class OAuthController {

    private final AppProperties appProperties;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final MemberRefreshTokenRepository memberRefreshTokenRepository;

//    @PostMapping("/login")
//    public ApiResponse login(HttpServletRequest request, HttpServletResponse response, @RequestBody ) {
//
//    }

    @GetMapping("/refresh")
    public ApiResponse refreshToken (HttpServletRequest request, HttpServletResponse response) {
        log.info("/oauth/refresh <- start!");
        // accessToken 검증
        String accessToken = HeaderUtil.getAccessToken(request);
        if (!jwtTokenUtil.validate(accessToken)) {
            return ApiResponse.invalidAccessToken();
        }

        // accessToken 만료 확인
        Claims claims = jwtTokenUtil.getExpiredTokenClaims(accessToken);
        if (claims == null) {
            return ApiResponse.notExpiredTokenYet();
        }

        Long memberId = Long.valueOf(claims.getSubject());
        Role role = Role.valueOf(claims.get("role", String.class));

        // refresh token
        String refreshToken = CookieUtils.getCookie(request, "refreshToken")
                .map(Cookie::getValue)
                .orElse((null));

        if (!jwtTokenUtil.validate(refreshToken)) {
            return ApiResponse.invalidRefreshToken();
        }

        Optional<MemberRefreshToken> memberRefreshToken =
                memberRefreshTokenRepository.findByMemberIdAndRefreshToken(memberId, refreshToken);
        if (memberRefreshToken.isEmpty()) {
            return ApiResponse.invalidRefreshToken();
        }

        Date now = new Date();
        String newAccessToken = jwtTokenUtil.createAccessToken(memberId, role);

        long remainingTime = jwtTokenUtil.getTokenClaims(refreshToken).getExpiration().getTime() - now.getTime();
        
        // 10시간 <- 20초
        if (remainingTime <= 20000) { //36000000
            String newRefreshToken = jwtTokenUtil.createRefreshToken();
            memberRefreshToken.orElseThrow().updateRefreshToken(newRefreshToken);

            CookieUtils.deleteCookie(request, response, "refreshToken");
            CookieUtils.addCookie(response, "refreshToken", newRefreshToken,
                    (int) (appProperties.getAuth().getRefreshTokenExpirationMsec() / 1000));
        }
        return ApiResponse.success("token", newAccessToken);
    }

}
