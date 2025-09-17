package com.example.ufo_fi.v2.user.presentation.dto.response;

import com.example.ufo_fi.v2.plan.domain.Plan;
import com.example.ufo_fi.v2.user.domain.User;
import com.example.ufo_fi.v2.userplan.domain.UserPlan;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoReadRes {

    @Schema(description = "닉네임")
    private String nickname;

    @Schema(description = "이메일")
    private String email;

    @Schema(description = "판매 가능한 모바일 데이터(실제 파는 것이 아님 그저 기준)")
    private Integer sellMobileDataCapacityGb;

    @Schema(description = "실제 판매 가능한 모바일 데이터")
    private Integer sellableDataAmount;

    @Schema(description = "zet 총량")
    private Integer zetAsset;

    @Schema(description = "프사입니다.")
    private String profilePhotoUrl;

    public static UserInfoReadRes of(final User user, final UserPlan userPlan, final Plan plan){
        return UserInfoReadRes.builder()
                .nickname(user.getNickname())
                .email(user.getEmail())
                .sellMobileDataCapacityGb(plan.getSellMobileDataCapacityGb())
                .sellableDataAmount(userPlan.getSellableDataAmount())
                .zetAsset(user.getZetAsset())
                .profilePhotoUrl(user.getProfilePhoto().getProfilePhotoUrl())
                .build();
    }
}
