package com.example.ufo_fi.v2.tradepost.presentation.dto.response;

import com.example.ufo_fi.v2.report.domain.Report;
import com.example.ufo_fi.v2.tradepost.domain.TradePost;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradePostReportRes {

    @Schema(description = "거래 게시물 제목")
    private String tradePostTitle;

    @Schema(description = "거래 게시물 신고 내용")
    private String reportContent;

    @Schema(description = "거래 게시물 숫자")
    private Integer reportCount;

    public static TradePostReportRes of(final Report report, final TradePost tradePost,
        int reportCount) {
        return TradePostReportRes.builder()
            .tradePostTitle(tradePost.getTitle())
            .reportContent(report.getContent())
            .reportCount(reportCount)
            .build();
    }
}
