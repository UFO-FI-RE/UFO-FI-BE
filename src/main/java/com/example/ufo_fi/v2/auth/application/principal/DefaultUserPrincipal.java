package com.example.ufo_fi.v2.auth.application.principal;

import com.example.ufo_fi.v2.user.domain.Role;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.nio.file.attribute.UserPrincipal;

/**
 * JWT 인증 완료 시 SecurityContextHolder에 담기는 값이다.
 *
 * @AuthenticationPrincipal 사용 시 받아올 수 있다.
 */

@Getter
@Builder
@AllArgsConstructor
public class DefaultUserPrincipal implements UserPrincipal {
    private Long id;
    private Role role;

    @Deprecated
    @Override
    public String getName() {
        return "빈 값";
    }

    // TODO: 타입 파싱은 암묵적으로 각 필드와 동일하다고 가정한다.(타입 체크 생략. 후에 리팩토링 시 추가 예정)
    public static DefaultUserPrincipal from(Claims claims) {
        return DefaultUserPrincipal.builder()
                .id(Long.parseLong(claims.get("id").toString()))
                .role(Role.valueOf((String) claims.get("role")))
                .build();
    }
}
