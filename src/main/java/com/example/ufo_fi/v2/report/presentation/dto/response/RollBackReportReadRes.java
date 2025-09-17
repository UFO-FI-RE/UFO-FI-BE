package com.example.ufo_fi.v2.report.presentation.dto.response;

import com.example.ufo_fi.v2.tradepost.domain.TradePost;
import com.example.ufo_fi.v2.tradepost.domain.TradePostStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RollBackReportReadRes {

    @Schema(description = "신고된 게시글 아이디입니다.")
    private Long postId;

    @Schema(description = "신고된 게시글의 유저 아이디입니다.")
    private Long userId;

    @Schema(description = "신고된 게시글의 신고 수 입니다.")
    private Integer reportCount;

    @Schema(description = "신고된 게시글의 상태입니다.")
    private TradePostStatus tradePostStatus;

    @Schema(description = "신고된 게시글의 생성일입니다.")
    private LocalDateTime createdAt;

    @Schema(description = "신고된 게시글의 신고 사유입니다.")
    private List<String> reportContents;

    public static RollBackReportReadRes from(final TradePost tradePost) {
        return RollBackReportReadRes.builder()
            .postId(tradePost.getId())
            .userId(tradePost.getUser().getId())
            .reportCount(tradePost.getReports().size())
            .tradePostStatus(tradePost.getTradePostStatus())
            .createdAt(tradePost.getCreatedAt())
            .build();
    }
}
