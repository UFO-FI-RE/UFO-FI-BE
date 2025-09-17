package com.example.ufo_fi.v2.order.presentation.dto.response;

import com.example.ufo_fi.v2.order.domain.TradeHistory;
import com.example.ufo_fi.v2.plan.domain.Carrier;
import com.example.ufo_fi.v2.plan.domain.MobileDataType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseHistoryRes {

    @Schema(description = "요금제 판매 게시글 식별 번호")
    private Long postId;

    @Schema(description = "구매 내역 식별 번호")
    private Long purchaseHistoryId;

    @Schema(description = "요금제 판매 게시글 생성일")
    private LocalDateTime createdAt;

    @Schema(example = "SKT, KT, LGU", description = "요금제 통신사")
    private Carrier carrier;

    @Schema(description = "요금제 판매 게시글 제목")
    private String title;

    @Schema(description = "요금제 판매 게시글 총 ZET 가격")
    private Integer totalZet;

    @Schema(example = "SKT, KT, LGU", description = "모바일 데이터 타입")
    private MobileDataType mobileDataType;

    @Schema(description = "판매하는 총 GB입니다.")
    private Integer totalGB;

    public static PurchaseHistoryRes from(final TradeHistory tradeHistory) {
        return PurchaseHistoryRes.builder()
            .postId(tradeHistory.getTradePost().getId())
            .purchaseHistoryId(tradeHistory.getId())
            .createdAt(tradeHistory.getCreatedAt())
            .carrier(tradeHistory.getTradePost().getCarrier())
            .title(tradeHistory.getTradePost().getTitle())
            .totalZet(tradeHistory.getTradePost().getTotalZet())
            .mobileDataType(tradeHistory.getTradePost().getMobileDataType())
            .totalGB(tradeHistory.getTradePost().getSellMobileDataCapacityGb())
            .build();
    }
}
