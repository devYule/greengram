package com.green.greengram4.security.oauth2;

import com.green.greengram4.security.JwtTokenProvider;
import com.green.greengram4.security.MyPrincipal;
import com.green.greengram4.security.MyUserDetails;
import com.green.greengram4.security.common.AppProperties;
import com.green.greengram4.security.common.CookieUtils;
import com.green.greengram4.user.model.UserModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

import static com.green.greengram4.security.oauth2.Oauth2AuthenticationRequestBasedOnCookieRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@Slf4j
@Component
@RequiredArgsConstructor
public class Oauth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final Oauth2AuthenticationRequestBasedOnCookieRepository basedOnCookieRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AppProperties appProperties;
    private final CookieUtils cookieUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String targetUrl = this.determineTargetUrl(request, response, authentication);
        if (response.isCommitted()) {
            log.error("Response has already been committed. Unable to redirect to {}", targetUrl);
            return;
        }
        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Optional<String> redirectUri = cookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);
        if (redirectUri.isPresent() && !hasAuthorizedRedirectUri(redirectUri.get())) {
            throw new IllegalArgumentException("Sorry!, Unauthorized Redirect URI");
        }
        String targetUrl = redirectUri.orElse(getDefaultTargetUrl()); // defaultTargetUrl = "/"

        // CustomOauth2UserService.loadUser() 의 리턴값이 들어옴.
        MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();

        MyPrincipal myPrincipal = myUserDetails.getMyPrincipal();

        String at = jwtTokenProvider.generateAccessToken(myPrincipal);
        String rt = jwtTokenProvider.generateRefreshToken(myPrincipal);

        int rtCookieMax = appProperties.getJwt().getRefreshTokenCookieMaxAge();
        cookieUtils.deleteCookie(response, "rt");
        cookieUtils.setCookie(response, "rt", rt, rtCookieMax);

        UserModel userModel = myUserDetails.getUserModel();


        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("access_token", at)
                .queryParam("iuser", userModel.getIuser())
                .queryParam("nm", userModel.getNm()).encode() // 한글일수도 있어서 인코딩 필수
                .queryParam("pic", userModel.getPic())
                .queryParam("firebase_token", userModel.getFirebaseToken())
                .build().toUriString();

    }

    private void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        basedOnCookieRepository.removeAuthorizationRequestCookies(response);
    }

    private boolean hasAuthorizedRedirectUri(String redirectUri) {
        URI clientRedirectUri = URI.create(redirectUri);
        log.info("clientRedirectUri.getHost() = {}", clientRedirectUri.getHost());
        log.info("clientRedirectUri.getPort() = {}", clientRedirectUri.getPort());
        log.info("clientRedirectUri.getPath() = {}", clientRedirectUri.getPath());
        return appProperties.getOauth2().getAuthorizedRedirectUris()
                .stream()
                .anyMatch(stringUri -> {
                    URI authorizedUri = URI.create(stringUri);
                    return authorizedUri.getHost().equalsIgnoreCase(clientRedirectUri.getHost()) &&
                            authorizedUri.getPort() == clientRedirectUri.getPort();
                });
    }
}
