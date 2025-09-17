package com.example.ufo_fi.v2.payment.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaymentReq {

    @Schema(description = "주문번호(UUID)입니다.")
    private String orderId;

    @Schema(description = "주문 패키지 명입니다.")
    private String packageName;

    @Schema(description = "주문 zet량입니다.")
    private Integer amount;

    @Schema(description = "주문 패키지 가격입니다.")
    private Integer price;
}
