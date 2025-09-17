package com.example.ufo_fi.v2.payment.presentation;

import com.example.ufo_fi.global.response.ResponseBody;
import com.example.ufo_fi.v2.auth.application.principal.DefaultUserPrincipal;
import com.example.ufo_fi.v2.payment.application.PaymentService;
import com.example.ufo_fi.v2.payment.presentation.api.PaymentApiSpec;
import com.example.ufo_fi.v2.payment.presentation.dto.request.ConfirmReq;
import com.example.ufo_fi.v2.payment.presentation.dto.request.PaymentReq;
import com.example.ufo_fi.v2.payment.presentation.dto.request.ZetRecoveryReq;
import com.example.ufo_fi.v2.payment.presentation.dto.response.ConfirmRes;
import com.example.ufo_fi.v2.payment.presentation.dto.response.FailLogRes;
import com.example.ufo_fi.v2.payment.presentation.dto.response.PaymentBackOfficesRes;
import com.example.ufo_fi.v2.payment.presentation.dto.response.PaymentRes;
import com.example.ufo_fi.v2.payment.presentation.dto.response.ZetRecoveryRes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PaymentController implements PaymentApiSpec {

    private final PaymentService chargeService;

    /**
     * 충전하기 요청
     * 결제 임시 정보 반환
     */
    @Override
    public ResponseEntity<ResponseBody<PaymentRes>> chargeZet(
            PaymentReq paymentReq,
            DefaultUserPrincipal defaultUserPrincipal) {

        return ResponseEntity
                .ok(ResponseBody
                        .success(chargeService.charge(defaultUserPrincipal.getId(), paymentReq)));
    }

    /**
     * 검증 및 승인 요청
     */
    @Override
    public ResponseEntity<ResponseBody<ConfirmRes>> confirm(
            ConfirmReq request,
            DefaultUserPrincipal defaultUserPrincipal) {

        return ResponseEntity
                .ok(ResponseBody
                        .success(chargeService.confirm(request)));
    }

    public ResponseEntity<ResponseBody<ZetRecoveryRes>> zetRecovery(
            ZetRecoveryReq zetRecoveryReq,
            DefaultUserPrincipal defaultUserPrincipal
    ) {
        return ResponseEntity.ok(
                ResponseBody.success(
                        chargeService.zetRecovery(zetRecoveryReq)));
    }

    @Override
    public ResponseEntity<ResponseBody<PaymentBackOfficesRes>> readPayments() {
        return ResponseEntity.ok(
            ResponseBody.success(
                chargeService.readPayments()));
    }

    @Override
    public ResponseEntity<ResponseBody<FailLogRes>> readFailLog(Long paymentId) {
        return ResponseEntity.ok(
            ResponseBody.success(
                chargeService.readFailLog(paymentId)));
    }

}
