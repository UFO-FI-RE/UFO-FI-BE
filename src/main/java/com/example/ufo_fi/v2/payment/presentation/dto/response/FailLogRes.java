package com.example.ufo_fi.v2.payment.presentation.dto.response;

import com.example.ufo_fi.v2.payment.domain.payment.entity.FailLog;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FailLogRes {

    @Schema(description = "실패 주문 키이다.")
    private String orderId;

    @Schema(description = "클라이언트의 요청이다.")
    private String confirmReq;

    @Schema(description = "토스 응답이다.")
    private String confirmResult;

    @Schema(description = "메서드 전이 흐름이다.")
    private String methodTrace;

    public static FailLogRes from(final FailLog failLog) {
        return FailLogRes.builder()
            .orderId(failLog.getOrderId())
            .confirmReq(failLog.getConfirmReq())
            .confirmResult(failLog.getConfirmResult())
            .methodTrace(failLog.getMethodTrace())
            .build();
    }
}
