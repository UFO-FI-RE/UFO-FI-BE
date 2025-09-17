package com.example.ufo_fi.v2.order.presentation.dto.response;

import com.example.ufo_fi.v2.order.domain.TradeHistory;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SaleHistoryReportedRes extends SaleHistoryRes {

    @Schema(description = "신고 내용")
    private String reportContent;

    @Schema(description = "신고 일자")
    private LocalDateTime reportDateTime;

    public static SaleHistoryReportedRes from(TradeHistory history) {
        return SaleHistoryReportedRes.builder()
            .postId(history.getTradePost().getId())
            .status(history.getTradePost().getTradePostStatus())
            .createdAt(history.getTradePost().getCreatedAt())
            .carrier(history.getTradePost().getCarrier())
            .title(history.getTradePost().getTitle())
            .totalZet(history.getTradePost().getTotalZet())
            .mobileDataType(history.getTradePost().getMobileDataType())
            .sellMobileDataAmountGB(history.getTradePost().getSellMobileDataCapacityGb())
            .reportContent(history.getTradePost().getReports().get(0).getContent())
            .reportDateTime(history.getTradePost().getReports().get(0).getCreatedAt())
            .build();
    }
}
