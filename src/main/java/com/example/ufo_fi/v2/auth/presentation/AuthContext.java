package com.example.ufo_fi.v2.auth.presentation;

import com.example.ufo_fi.v2.user.domain.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
final public class AuthContext {
    private final Long userId;
    private final Role role;

    @Builder
    private AuthContext(Long userid, Role role) {
        this.userId = userid;
        this.role = role;
    }

    public static AuthContext of(Long userId, Role role){
        return AuthContext.builder()
                .userid(userId)
                .role(role)
                .build();
    }
}
