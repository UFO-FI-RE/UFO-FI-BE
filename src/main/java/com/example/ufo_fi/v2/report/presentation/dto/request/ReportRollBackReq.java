package com.example.ufo_fi.v2.report.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReportRollBackReq {

    @Schema(description = "신고 해제를 위한 신고 게시물 식별 번호")
    private Long tradePostId;
}
