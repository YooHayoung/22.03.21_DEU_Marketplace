package com.deu.marketplace.utils;

import com.deu.marketplace.config.AppProperties;
import com.deu.marketplace.config.auth.UserPrincipal;
import com.deu.marketplace.config.auth.UserPrincipal;
import com.deu.marketplace.domain.member.entity.Role;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
//@Service
@RequiredArgsConstructor
public class JwtTokenUtil {

    private final AppProperties appProperties;
    private static final String AUTHORITIES_KEY = "role";

    public String createAccessToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + appProperties.getAuth().getAccessTokenExpirationMsec());

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, appProperties.getAuth().getTokenSecret())
                .setSubject(Long.toString(userPrincipal.getMemberId()))
                .claim(AUTHORITIES_KEY, userPrincipal.getAuthorities())
                .setIssuer("DeuMarket")
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .compact();
    }

    public String createAccessToken(Long memberId, Role role) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + appProperties.getAuth().getAccessTokenExpirationMsec());

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, appProperties.getAuth().getTokenSecret())
                .setSubject(Long.toString(memberId))
                .claim(AUTHORITIES_KEY, role.getKey())
                .setIssuer("DeuMarket")
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .compact();
    }

    public String createRefreshToken() {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + appProperties.getAuth().getRefreshTokenExpirationMsec());

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, appProperties.getAuth().getTokenSecret())
                .setIssuer("DeuMarket")
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .compact();
    }



    public Long getMemberIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(appProperties.getAuth().getTokenSecret())
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    public boolean validate(String authToken) {
        return this.getTokenClaims(authToken) != null;
    }

    public Claims getTokenClaims(String authToken) {
        try {
            return Jwts.parser()
                    .setSigningKey(appProperties.getAuth().getTokenSecret())
                    .parseClaimsJws(authToken)
                    .getBody();
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return null;
    }

    public Claims getExpiredTokenClaims(String authToken) {
        try {
            Jwts.parser()
                    .setSigningKey(appProperties.getAuth().getTokenSecret())
                    .parseClaimsJws(authToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
            return e.getClaims();
        }
        return null;
    }
}
