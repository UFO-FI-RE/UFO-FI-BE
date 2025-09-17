package com.example.ufo_fi.v2.tradepost.presentation.dto.request;

import com.example.ufo_fi.v2.plan.domain.Carrier;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TradePostQueryReq {

    @Schema(description = "통신사")
    private Carrier carrier;

    @Schema(description = "최대 ZET")
    private Integer maxTotalZet;

    @Schema(description = "최소 ZET")
    private Integer minTotalZet;

    @Schema(description = "최대 데이터량")
    private Integer maxCapacity;

    @Schema(description = "최소 데이터량")
    private Integer minCapacity;

    @Schema(description = "평판")
    private String reputation;

    @Schema(description = "커서 스크롤 기준 식별자(스크롤)")
    private Long cursorId;

    @Schema(description = "사이즈?")
    private Integer size;

}
