package com.green.greengram4.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

/**
 * 토큰 검증
 * 잘못된 토큰, 만료된 토큰, 지원하지 않는 토큰 의 경우 응답시 해당 메소드 실행.
 */
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    /**
     * 토큰 검증
     * 잘못된 토큰, 만료된 토큰, 지원하지 않는 토큰 의 경우 응답시 해당 메소드 실행.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}

