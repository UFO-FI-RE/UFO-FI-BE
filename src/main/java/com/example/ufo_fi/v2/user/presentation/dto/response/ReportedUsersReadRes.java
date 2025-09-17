package com.example.ufo_fi.v2.user.presentation.dto.response;

import com.example.ufo_fi.v2.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportedUsersReadRes {

    @Schema(description = "신고된 유저 아이디입니다.")
    private Page<ReportedUserReadRes> reportedUsersReadRes;

    public static ReportedUsersReadRes from(Page<User> reportedUsers) {
        return ReportedUsersReadRes.builder()
            .reportedUsersReadRes(reportedUsers
                .map(ReportedUserReadRes::from))
            .build();
    }
}
