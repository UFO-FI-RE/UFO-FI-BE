package com.example.ufo_fi.v2.statistics.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsReportsRes {

    @Schema(description = "모든 사용자 수 입니다.")
    private Long allUsersCount;

    @Schema(description = "비활성화된(ROLE_REPORTED) 사용자 수 입니다.")
    private Long reportedUsersCount;

    @Schema(description = "모든 신고가 3회 이상인 게시물 수 입니다.")
    private Long pendingReportPostCount;

    @Schema(description = "모든 신고 확정된 게시물 수 입니다.")
    private Long confirmedReportPostCount;

    public static StatisticsReportsRes of(
        Long allUsersCount,
        Long reportedUsersCount,
        Long pendingReportPostCount,
        Long confirmedReportPostCount
    ){
        return StatisticsReportsRes.builder()
            .allUsersCount(allUsersCount)
            .reportedUsersCount(reportedUsersCount)
            .pendingReportPostCount(pendingReportPostCount)
            .confirmedReportPostCount(confirmedReportPostCount)
            .build();
    }
}
