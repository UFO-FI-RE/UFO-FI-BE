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
public class StatisticsRes {

    @Schema(description = "모든 사용자의 머릿수")
    private Long allUsersCount;

    @Schema(description = "신고되지 않은 모든 회원 수")
    private Long notReportedUsersCount;

    @Schema(description = "모든 거래 게시물 수")
    private Long allTradePostsCount;

    @Schema(description = "모든 신고 건수")
    private Long allReportCount;

    public static StatisticsRes of(
        Long allUsersCount,
        Long notReportedUsersCount,
        Long allTradePostsCount,
        Long allReportCount
    ){
        return StatisticsRes.builder()
            .allUsersCount(allUsersCount)
            .notReportedUsersCount(notReportedUsersCount)
            .allTradePostsCount(allTradePostsCount)
            .allReportCount(allReportCount)
            .build();
    }
}
