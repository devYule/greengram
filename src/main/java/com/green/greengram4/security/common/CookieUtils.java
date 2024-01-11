package com.green.greengram4.security.common;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class CookieUtils {

    /*
    필요한 어노테이션들
    @Component
     */

    public Cookie getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            return Arrays.stream(cookies).filter(c -> c.getName().equalsIgnoreCase(name)).findAny().orElse(null);
        }
        return null;
    }

    public void setCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/"); // 쿠키를 가져오게 만들 uri
        cookie.setHttpOnly(true); // !!    httpOnly 로 만들기
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    /**
     * 쿠키 지우기
     *
     * @param response
     * @param name
     */
    public void deleteCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setMaxAge(0);
        cookie.setValue(null);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

}
