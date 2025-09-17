package com.example.ufo_fi.v2.order.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TradePostPurchaseReq {

    @Schema(description = "게시물 식별 번호입니다.")
    private Long postId;

    @Schema(description = "판매자 식별 번호입니다.")
    private Long sellerId;

    @Schema(description = "최종 제트")
    private Integer totalZet;

    @Schema(description = "판매하는 모바일량 GB")
    private Integer sellMobileDataAmountGB;
}
