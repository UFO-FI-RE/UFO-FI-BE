package com.example.ufo_fi.v2.userplan.presentation.dto.response;

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
public class UserPlanUpdateRes {

    @Schema(description = "유저 요금제 아이디")
    private Long userPlanId;

    public static UserPlanUpdateRes from(UserPlan userPlan) {
        return UserPlanUpdateRes.builder()
                .userPlanId(userPlan.getId())
                .build();
    }
}
