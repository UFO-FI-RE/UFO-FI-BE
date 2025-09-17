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
public class SaleHistorySoldOutRes extends SaleHistoryRes {

    @Schema(description = "판매 날짜")
    private LocalDateTime saleDateTime;

    @Schema(description = "현제 나에게 남은 ZET")
    private Integer remainZet;

    public static SaleHistorySoldOutRes from(final TradeHistory history) {
        return SaleHistorySoldOutRes.builder()
            .postId(history.getTradePost().getId())
            .status(history.getTradePost().getTradePostStatus())
            .createdAt(history.getTradePost().getCreatedAt())
            .carrier(history.getTradePost().getCarrier())
            .title(history.getTradePost().getTitle())
            .totalZet(history.getTradePost().getTotalZet())
            .mobileDataType(history.getTradePost().getMobileDataType())
            .sellMobileDataAmountGB(history.getTradePost().getSellMobileDataCapacityGb())
            .saleDateTime(history.getCreatedAt())
            .remainZet(history.getUser().getZetAsset())
            .build();
    }
}