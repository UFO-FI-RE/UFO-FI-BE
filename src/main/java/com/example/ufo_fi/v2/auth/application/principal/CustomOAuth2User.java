package com.example.ufo_fi.v2.auth.application.principal;

import com.example.ufo_fi.v2.user.domain.Role;
import com.example.ufo_fi.v2.user.domain.User;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

/**
 * JWT가 아닌 OAuth2.0 인증 시 @AuthenticationPrincipal로 받을 수 있는 객체
 * 아래 2개의 Deprecated는 필요없다.
 * getAuthorities만이 인가 시 필요함
 */

@Getter
@Builder
@AllArgsConstructor
public class CustomOAuth2User implements OAuth2User {
    private Long id;
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role.toString()));
    }

    @Deprecated
    @Override
    public String getName() {
        return "빈 값";
    }

    @Deprecated //이 메서드는 사용하지 않음
    @Override
    public Map<String, Object> getAttributes() {
        return Map.of();
    }

    public static CustomOAuth2User from(User user){
        return CustomOAuth2User.builder()
                .id(user.getId())
                .role(user.getRole())
                .build();
    }
}
