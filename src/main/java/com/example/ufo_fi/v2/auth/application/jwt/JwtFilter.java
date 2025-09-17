package com.example.ufo_fi.v2.auth.application.jwt;

import com.example.ufo_fi.v2.auth.application.oauth.CookieUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * JWT토큰을 검사한다. JWT토큰을 통해 인증을 한다.
 */

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CookieUtil cookieUtil;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String uri = request.getRequestURI();

        log.info(uri);
        return uri.startsWith("/login")
            || uri.startsWith("/oauth2")
            || uri.startsWith("/auth")
            || uri.startsWith("/refresh")
            || uri.startsWith("/test")
            || uri.startsWith("/actuator")
            || uri.startsWith("/swagger")
            || uri.startsWith("/v3")
            || uri.startsWith("/webjars")
            || uri.startsWith("/monitor")
            || uri.startsWith("/metrics")
            || uri.startsWith("/prometheus")
            ;
    }

    /**
     * JWT 필터의 동작
     * 1. JWT를 검증한다.
     * 2. Bearer를 제외한 jwt(String)을 꺼내온다.
     * 3. SecurityContextHolder(ThreadLocal)에 UserDetail을 세팅
     * 4. 다음 필터로 넘긴다.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain)
        throws AuthenticationException, ServletException, IOException {
        jwtUtil.validate(cookieUtil.getCookie("Authorization", request), response);
        String jwt = resolveToken(request);
        setSecurityContextHolder(jwt);
        filterChain.doFilter(request, response);
    }

    //cookie에서 jwt토큰을 빼온다.
    private String resolveToken(HttpServletRequest request) {
        return cookieUtil.getCookie("Authorization", request).getValue();
    }

    //securityContextHolder에 jwt를 Authentication으로 바꾸어 저장한다.
    private void setSecurityContextHolder(String jwt) {
        Authentication authentication = jwtUtil.toAuthentication(jwt);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
