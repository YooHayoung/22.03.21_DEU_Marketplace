package com.deu.marketplace.utils;

import com.deu.marketplace.config.AppProperties;
import com.deu.marketplace.config.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;

@Component
//@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private  JwtTokenUtil jwtTokenUtil;
    private  AppProperties appProperties;
    private  HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    public OAuth2SuccessHandler(@Lazy JwtTokenUtil jwtTokenUtil,@Lazy AppProperties appProperties,@Lazy HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.appProperties = appProperties;
        this.httpCookieOAuth2AuthorizationRequestRepository = httpCookieOAuth2AuthorizationRequestRepository;
    }

    //oauth2인증이 성공적으로 이뤄졌을 때 실행된다
    //token을 포함한 uri을 생성 후 인증요청 쿠키를 비워주고 redirect 한다.
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        logger.info("onAuthenticationSuccess");
        String targetUrl = determineTargetUrl(request, response, authentication);
        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }
        clearAuthenticationAttributes(request, response);
//        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        CookieUtils.addCookie(response, "accessToken", createAccessToken(authentication), (int) (appProperties.getAuth().getAccessTokenExpirationMsec()/1000));
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    //token을 생성하고 이를 포함한 프론트엔드로의 uri를 생성한다.
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        logger.info("determineTargetUrl");
        Optional<String> redirectUri = CookieUtils.getCookie(request, HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);
//        if(redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
//            try {
//                throw new Exception("Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());
        return UriComponentsBuilder.fromUriString(targetUrl)
                .build().toUriString();
    }

    public String createAccessToken(Authentication authentication) {
        return jwtTokenUtil.createAccessToken(authentication);
    }

    //인증정보 요청 내역을 쿠키에서 삭제한다.
    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

    //application.properties에 등록해놓은 Redirect uri가 맞는지 확인한다. (app.redirect-uris)
//    private boolean isAuthorizedRedirectUri(String uri) {
//        logger.info("redirectUri = " + uri);
//        logger.info("app-redirectUri = " + appProperties.getOauth2().getAuthorizedRedirectUris());
//        URI clientRedirectUri = URI.create(uri);
//        return appProperties.getOauth2().getAuthorizedRedirectUris()
//                .stream()
//                .anyMatch(authorizedRedirectUri -> {
//                    // Only validate host and port. Let the clients use different paths if they want to
//                    URI authorizedURI = URI.create(authorizedRedirectUri);
//                    if(authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
//                            && authorizedURI.getPort() == clientRedirectUri.getPort()) {
//                        return true;
//                    }
//                    return false;
//                });
//    }
}
