package com.example.ufo_fi.v2.auth.application.oauth;

import com.example.ufo_fi.v2.auth.domain.Refresh;
import com.example.ufo_fi.v2.auth.domain.RefreshManager;
import com.example.ufo_fi.v2.auth.application.jwt.JwtUtil;
import com.example.ufo_fi.v2.auth.application.principal.CustomOAuth2User;
import com.example.ufo_fi.v2.auth.application.refresh.RefreshUtil;
import com.example.ufo_fi.v2.user.domain.User;
import com.example.ufo_fi.v2.user.domain.UserManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Authentication에서 여러가지 정보를 가져와 JWT토큰을 생성해준다.
 * 또한 RefreshToken도 생성해준다.
 * 그 후, response Header에 담아 클라이언트로 보내준다.
 * 클라이언트는 이를 LocalStoreage에 담아 줄 것이다.
 * 매 요청마다, JWT 토큰만, 헤더에 담아 요청을 보내주어야 한다.
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomOAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Value("${login.success-url}")
    private String successUrl;

    @Value("${jwt.access-token-validity-ms}")
    private long jwtTokenValidityMs;

    private final JwtUtil jwtUtil;
    private final CookieUtil cookieUtil;
    private final RefreshUtil refreshUtil;
    private final UserManager userManager;
    private final RefreshManager refreshManager;

    /**
     * OAuth2 인증 성공 시 실행하는 로직
     * jwt 토큰과 refresh 토큰을 생성하고 헤더에 담아 프론트에게 보내준다.
     * jwt 토큰이 header에 존재한다면, 그냥 자동으로 갱신해준다.
     * refresh 토큰은 생성 시 DB에 저장해준다.
     */
    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        CustomOAuth2User customOAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        String jwt = generateJwt(customOAuth2User);             //jwt 생성
        String refresh = generateRefresh(customOAuth2User);     //refresh token 생성
        saveRefreshToken(customOAuth2User, refresh);            //리프레시 토큰을 저장
        setCookieJwt(jwt, response);                            //jwt 토큰 쿠키 저장
        setCookieRefresh(refresh, response);                    //refresh 토큰 쿠키 저장

        log.info("JWT {}", jwt);
        log.info("Refresh-Token {}", refresh);

        response.sendRedirect(successUrl);
    }

    //jwt 토큰 생성
    private String generateJwt(CustomOAuth2User customOAuth2User) {
        return jwtUtil.generateJwt(customOAuth2User.getId(), customOAuth2User.getRole());
    }

    //리프레시 토큰 생성
    private String generateRefresh(CustomOAuth2User customOAuth2User) {
        return refreshUtil.generateRefresh(customOAuth2User.getId());
    }

    //리프레시 토큰 저장
    private void saveRefreshToken(CustomOAuth2User customOAuth2User, String rawRefresh) {
        User user = userManager.findById(customOAuth2User.getId());
        Refresh refresh = Refresh.of(rawRefresh);
        user.registerRefresh(refresh);
        refreshManager.save(refresh);
    }

    //쿠키에 jwt 토큰 세팅
    private void setCookieJwt(String jwt, HttpServletResponse response) {
        cookieUtil.setResponseBasicCookie("Authorization", jwt, jwtTokenValidityMs, response);
    }

    //쿠키에 리프레시 토큰 세팅
    private void setCookieRefresh(String refresh, HttpServletResponse response){
        cookieUtil.setResponseBasicCookie("Refresh", refresh, 604800000, response);
    }
}
