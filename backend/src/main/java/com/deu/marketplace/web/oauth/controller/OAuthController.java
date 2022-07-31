package com.deu.marketplace.web.oauth.controller;


import com.deu.marketplace.common.ApiResponse;
import com.deu.marketplace.config.AppProperties;
import com.deu.marketplace.domain.member.entity.Role;
import com.deu.marketplace.domain.memberRefreshToken.entity.MemberRefreshToken;
import com.deu.marketplace.domain.memberRefreshToken.repository.MemberRefreshTokenRepository;
import com.deu.marketplace.domain.memberRefreshToken.service.MemberRefreshTokenService;
import com.deu.marketplace.utils.CookieUtils;
import com.deu.marketplace.utils.HeaderUtil;
import com.deu.marketplace.utils.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class OAuthController {

    private final AppProperties appProperties;
    private final JwtTokenUtil jwtTokenUtil;
    private final MemberRefreshTokenRepository memberRefreshTokenRepository;
    private final MemberRefreshTokenService memberRefreshTokenService;

    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.naver.client-secret}")
    private String clientSecret;

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


    @GetMapping("/logout")
    public ApiResponse logout(@AuthenticationPrincipal Long memberId) {
        memberRefreshTokenService.deleteByMemberId(memberId);
        return ApiResponse.success("logout", "Logout Success");
    }


    private String requestToServer(String apiURL) throws IOException {
        return requestToServer(apiURL, null);
    }
    private String requestToServer(String apiURL, String headerStr) throws IOException {
        URL url = new URL(apiURL);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("GET");
        System.out.println("header Str: " + headerStr);
        if(headerStr != null && !headerStr.equals("") ) {
            con.setRequestProperty("Authorization", headerStr);
        }
        int responseCode = con.getResponseCode();
        BufferedReader br;
        System.out.println("responseCode="+responseCode);
        if(responseCode == 200) { // 정상 호출
            br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        } else {  // 에러 발생
            br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
        }
        String inputLine;
        StringBuffer res = new StringBuffer();
        while ((inputLine = br.readLine()) != null) {
            res.append(inputLine);
        }
        br.close();
        if(responseCode==200) {
            String new_res=res.toString().replaceAll("&#39;", "");
            return new_res;
        } else {
            return null;
        }
    }

    @GetMapping("/callback/*")
    public void test(HttpServletRequest request,
                     @RequestParam("code") String code, @RequestParam("state") String state) {
        String oauthState = (String)request.getSession().getAttribute("oauthState");
        log.info("oauth state : " + oauthState);
        log.info("code : " + code);
        log.info("state : " + state);
    }

}
