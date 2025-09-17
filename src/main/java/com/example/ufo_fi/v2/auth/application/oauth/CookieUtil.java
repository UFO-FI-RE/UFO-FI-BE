package com.example.ufo_fi.v2.auth.application.oauth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

@Component
public class CookieUtil {

    //추후 보안 공부 해볼것.
    // TODO: 배포->로컬, 로컬->배포 시 쿠키 설정 바꾸기
    public void setResponseBasicCookie(String key, String value, long expiredMs, HttpServletResponse response) {
        String cookieValue = String.format(
            "%s=%s; Path=/; Max-Age=%d; HttpOnly; Domain=.ufo-fi.store; SameSite=None; Secure",
                key, value, expiredMs);

        //"%s=%s; Path=/; Max-Age=%d; HttpOnly; Domain=.ufo-fi.store; SameSite=None; Secure"
        //"%s=%s; Path=/; Max-Age=%d; HttpOnly; "
        response.addHeader("Set-Cookie", cookieValue);
    }

    public void deleteCookie(String key, String value, HttpServletResponse response) {
        Cookie cookie = new Cookie(key, value);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    public Cookie getCookie(String key, HttpServletRequest request) {
        return WebUtils.getCookie(request, key);
    }
}
