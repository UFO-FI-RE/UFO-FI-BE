package com.example.ufo_fi.v2.auth.application;

import com.example.ufo_fi.v2.auth.application.jwt.JwtUtil;
import com.example.ufo_fi.v2.auth.application.refresh.RefreshUtil;
import com.example.ufo_fi.v2.auth.persistence.RefreshRepository;
import com.example.ufo_fi.v2.user.domain.User;
import com.example.ufo_fi.v2.user.domain.UserManager;
import com.nimbusds.oauth2.sdk.token.RefreshToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LogoutService {
    private final JwtUtil jwtUtil;
    private final RefreshUtil refreshUtil;
    private final UserManager userManager;

    @Transactional
    public void logout(HttpServletRequest request, HttpServletResponse response, Long userId){
        // 1. 응답으로 만료시간이 다 된 jwt쿠키를 추가하여, 쿠키를 삭제한다.
        jwtUtil.deleteJwtCookie(response);
        refreshUtil.deleteRefreshCookie(response);

        User user = userManager.findById(userId);
        user.deleteRefresh();
    }
}
