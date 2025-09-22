package com.example.ufo_fi.v2.auth.presentation;

import com.example.ufo_fi.v2.user.domain.Role;

final public class AuthContextHolder {
    private static final ThreadLocal<AuthContext> authContextHolder = new ThreadLocal<>();

    public static void loginSuccess(AuthContext authContext) {
        authContextHolder.set(authContext);
    }

    public static Long getUserId() {
        return authContextHolder.get().getUserId();
    }

    public static Role getRole() {
        return authContextHolder.get().getRole();
    }

    public static void clear() {
        authContextHolder.remove();
    }
}
