package com.example.ufo_fi.v2.payment.application;

import com.example.ufo_fi.v2.payment.infrastructure.toss.response.ConfirmResult;
import com.example.ufo_fi.v2.payment.infrastructure.toss.request.ConfirmCommand;

public interface PaymentClient {
    ConfirmResult confirmPayment(ConfirmCommand confirmCommand);
}
