package com.example.ufo_fi.v2.tradepost.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TradePostReportReq {

    @Schema(description = "신고 내용")
    private String content;

    @Schema(description = "게시물 주인 사용자 식별 번호")
    private Long postOwnerUserId;
}
