package com.example.ufo_fi.v2.payment.infrastructure.toss.response;

import com.example.ufo_fi.v2.payment.domain.payment.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmTimeoutResult implements ConfirmResult {
    private String code = String.valueOf(HttpStatus.REQUEST_TIMEOUT);
    private String message = "토스 응답 타임아웃입니다.";

    @Override
    public PaymentStatus resultStatus() {
        return PaymentStatus.TIMEOUT;
    }
}
