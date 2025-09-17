package com.example.ufo_fi.v2.payment.infrastructure.toss.response;

import com.example.ufo_fi.v2.payment.domain.payment.PaymentStatus;

public interface ConfirmResult {
    PaymentStatus resultStatus();
}
