package com.example.ufo_fi.v2.payment.presentation.dto.response;

import com.example.ufo_fi.v2.payment.domain.payment.entity.Payment;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentBackOfficesRes {

    private List<PaymentBackOfficeRes> paymentBackOfficesRes;

    public static PaymentBackOfficesRes from(final List<Payment> payments) {
        return PaymentBackOfficesRes.builder()
            .paymentBackOfficesRes(
                payments.stream()
                    .map(PaymentBackOfficeRes::from)
                    .toList()
            )
            .build();
    }
}
