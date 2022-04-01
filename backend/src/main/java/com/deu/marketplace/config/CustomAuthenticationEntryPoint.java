package com.deu.marketplace.config;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
////        response.addHeader("WWW-Authenticate", "Basic realm=" + super.getRealmName() + "");
//
//        log.info("HTTP Status 401 - " + authException.getMessage());
        log.debug("exception : {}", authException);
        String exception = (String) request.getAttribute("exception");
//        OAuth2ErrorCodes errorCodes;

        log.debug("exception : {}", exception);

        if (exception == null) {
            response.setStatus(HttpStatus.SC_FORBIDDEN);
            return;
        }
    }
}
