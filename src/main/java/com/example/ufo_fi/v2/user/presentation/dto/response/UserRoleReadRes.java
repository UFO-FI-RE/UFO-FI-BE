package com.example.ufo_fi.v2.user.presentation.dto.response;

import com.example.ufo_fi.v2.user.domain.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleReadRes {

    @Schema(example = """
                    ROLE_USER,
                    ROLE_ADMIN,
                    ROLE_NO_INFO,
                    ROLE_REPORTED
            """, description = "현재 유저의 롤을 가져온다.")
    private Role role;

    @Schema(description = "유저의 핸드폰 번호")
    private String phoneNumber;

    public static UserRoleReadRes from(Role role, String phoneNumber) {
        return UserRoleReadRes.builder()
                .role(role)
                .phoneNumber(phoneNumber)
                .build();
    }
}
