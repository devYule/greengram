package com.green.greengram4.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.greengram4.security.common.CookieUtils;
import com.green.greengram4.user.model.UserSignInResultVo;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


/**
 * 한번만 사용되는 필터 (OncePerRequestFilter extends)
 * <p>
 * 헤더에 토큰관련 값 이 존재한다면 해당 토큰에 저장된 값들을 저장함.
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    /**
     * 필요 어노테이션
     * - @Slf4j
     * - @RequiredArgsConstructor
     * - @Component
     */
    private final JwtTokenProvider jwtTokenProvider;
    private final CookieUtils cookieUtils;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtTokenProvider.getTokenFromHeader(request);
        log.info("JwtAuthentication-Token = {}", token);
        if (token != null) {
            if (jwtTokenProvider.isValidatedToken(token)) {
                // 저장 (SecurityContextHolder 에 저장 - 해당 Holder 에는 각 요청별로 정보(Authentication)를 담을 수 있는 공간이 있다.)
                Authentication auth = jwtTokenProvider.getAuthentication(token);
                if (auth != null) {
                    SecurityContextHolder.getContext().setAuthentication(auth);

                }
            } else {
                /* TODO: 1/11/24
                    이거 잘 작동되는지 체크.
                    --by Hyunmin */
                Cookie cookie = cookieUtils.getCookie(request, "rt");
                if (cookie != null) {
                    String value = cookie.getValue();
                    MyPrincipal myPrincipal = ((MyUserDetails) jwtTokenProvider.getUserDetailsFromToken(value)).getMyPrincipal();
                    response.setStatus(600);
                    response.getWriter().write(objectMapper.writeValueAsString(UserSignInResultVo.builder().result(1).accessToken(jwtTokenProvider.generateAccessToken(myPrincipal)).build()));
                    return;
                }
            }
        }

        log.info("checkClass = {}", this.getClass());
        filterChain.doFilter(request, response);
    }
}


