package com.example.ufo_fi.v2.order.presentation.dto.response;

import com.example.ufo_fi.v2.order.domain.TradeHistory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SaleHistoryExpiredRes extends SaleHistoryRes {

    private final static String content = "해당 게시글은 유효 기간이 지나, 삭제 처리되었습니다.";

    @Schema(description = "그냥 문자열")
    private String expiredContent;

    public static SaleHistoryExpiredRes from(TradeHistory history) {
        return SaleHistoryExpiredRes.builder()
            .postId(history.getTradePost().getId())
            .status(history.getTradePost().getTradePostStatus())
            .createdAt(history.getTradePost().getCreatedAt())
            .carrier(history.getTradePost().getCarrier())
            .title(history.getTradePost().getTitle())
            .totalZet(history.getTradePost().getTotalZet())
            .mobileDataType(history.getTradePost().getMobileDataType())
            .sellMobileDataAmountGB(history.getTradePost().getSellMobileDataCapacityGb())
            .expiredContent(content)
            .build();
    }
}
