package com.green.greengram4.security.oauth2;

import com.green.greengram4.security.common.CookieUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

import static com.green.greengram4.security.oauth2.Oauth2AuthenticationRequestBasedOnCookieRepository.*;

/**
 * 실패했을 때 호출되는 핸들러
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class Oauth2AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final CookieUtils cookieUtils;
    private final Oauth2AuthenticationRequestBasedOnCookieRepository basedOnCookieRepository;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.info("onAuthenticationFailure");
        String targetUrl = cookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME).map(Cookie::getValue).orElse("/");

        log.info("ex", exception);

        targetUrl = UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("error", exception.getLocalizedMessage())
                .build().toUriString();
        log.info("Oauth2AuthenticationFailureHandler.onAuthenticationFailure | targetUrl = {}", targetUrl);

        basedOnCookieRepository.removeAuthorizationRequestCookies(response);

        getRedirectStrategy().sendRedirect(request, response, targetUrl);

    }
}
