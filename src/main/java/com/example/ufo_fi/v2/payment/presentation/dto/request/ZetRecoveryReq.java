package com.example.ufo_fi.v2.payment.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ZetRecoveryReq {

    @Schema(description = "사용자 ID")
    private Long userId;

    @Schema(description = "복구 zet 금액")
    private Integer recoveryZet;

    @Schema(description = "주문 번호")
    private String orderId;
}
