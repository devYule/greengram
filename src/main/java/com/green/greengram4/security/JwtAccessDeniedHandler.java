package com.green.greengram4.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

@Slf4j
/**
 * 권한이 없을경우 호출 > 응답
 */
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    /**
     * 권한이 없을경우 호출 > 응답
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.info("class = {}", this.getClass());
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
    }
}
