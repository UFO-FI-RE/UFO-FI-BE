package com.example.ufo_fi.global.config;

import com.example.ufo_fi.v2.auth.presentation.LoginInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
    private final LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/auth/**",
                        "/health", "/actuator/**",
                        "/error",
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/docs/**",
                        "/css/**", "/js/**", "/images/**",
                        "/kakao"
                );
    }
}
