package com.example.ufo_fi.v2.payment.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ConfirmReq {

    @Schema(description = "멱등성을 위한 결제 키입니다.")
    private String paymentKey;

    @Schema(description = "주문 아이디(UUID)입니다.")
    private String orderId;

    @Schema(description = "충전할 zet량입니다.")
    private Integer amount;

    @Schema(description = "해당 패키지 가격입니다.")
    private Integer price;
}
