package com.example.ufo_fi.v2.payment.application;

import com.example.ufo_fi.v2.payment.domain.payment.PaymentStatus;
import com.example.ufo_fi.v2.payment.domain.payment.entity.FailLog;
import com.example.ufo_fi.v2.payment.domain.payment.entity.Payment;
import com.example.ufo_fi.v2.payment.presentation.dto.response.FailLogRes;
import com.example.ufo_fi.v2.payment.presentation.dto.response.PaymentBackOfficesRes;
import com.example.ufo_fi.v2.payment.presentation.dto.response.ZetRecoveryRes;
import com.example.ufo_fi.v2.user.domain.User;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {
    public ZetRecoveryRes toZetRecoveryRes(User user, PaymentStatus status) {
        return ZetRecoveryRes.builder()
                .userId(user.getId())
                .zet(user.getZetAsset())
                .paymentStatus(status)
                .build();
    }

    public PaymentBackOfficesRes toPaymentBackOfficesRes(List<Payment> payments) {
        return PaymentBackOfficesRes.from(payments);
    }

    public FailLogRes toFailLogRes(FailLog failLog) {
        return FailLogRes.from(failLog);
    }
}
