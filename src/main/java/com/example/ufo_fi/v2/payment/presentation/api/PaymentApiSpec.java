package com.example.ufo_fi.v2.payment.presentation.api;

import com.example.ufo_fi.global.response.ResponseBody;
import com.example.ufo_fi.v2.auth.application.principal.DefaultUserPrincipal;
import com.example.ufo_fi.v2.payment.presentation.dto.response.FailLogRes;
import com.example.ufo_fi.v2.payment.presentation.dto.response.PaymentBackOfficesRes;
import com.example.ufo_fi.v2.payment.presentation.dto.request.ConfirmReq;
import com.example.ufo_fi.v2.payment.presentation.dto.request.PaymentReq;
import com.example.ufo_fi.v2.payment.presentation.dto.request.ZetRecoveryReq;
import com.example.ufo_fi.v2.payment.presentation.dto.response.ConfirmRes;
import com.example.ufo_fi.v2.payment.presentation.dto.response.PaymentRes;
import com.example.ufo_fi.v2.payment.presentation.dto.response.ZetRecoveryRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Tag(name = "Payment API", description = "충전하기 API")
public interface PaymentApiSpec {

    @Operation(summary = "충전하기 API", description = "ZET 충전을 시도합니다.")
    @ApiResponse(useReturnTypeSchema = true)
    @PostMapping("/payment")
    ResponseEntity<ResponseBody<PaymentRes>> chargeZet(
            @RequestBody PaymentReq request,
            @AuthenticationPrincipal DefaultUserPrincipal defaultUserPrincipal
    );


    @Operation(summary = "결제 정보 검증 및 승인 API", description = "결제 정보를 검증하고 최종 결제 승인을 요청합니다.")
    @ApiResponse(useReturnTypeSchema = true)
    @PostMapping("/payment/status")
    ResponseEntity<ResponseBody<ConfirmRes>> confirm(
            @RequestBody ConfirmReq request,
            @AuthenticationPrincipal DefaultUserPrincipal defaultUserPrincipal
    );

    @Operation(summary = "관리자 zet 복구 API", description = "관리자 권한으로 zet를 복구한다.")
    @ApiResponse(useReturnTypeSchema = true)
    @PostMapping("/admin/zet-recovery")
    ResponseEntity<ResponseBody<ZetRecoveryRes>> zetRecovery(
            @RequestBody ZetRecoveryReq zetRecoveryReq,
            @AuthenticationPrincipal DefaultUserPrincipal defaultUserPrincipal
    );

    @Operation(summary = "관리자 payment 리스트 조회", description = "관리자 권한으로 실패 로그가 있는 payments를 조회한다.")
    @ApiResponse(useReturnTypeSchema = true)
    @GetMapping("/admin/payments")
    ResponseEntity<ResponseBody<PaymentBackOfficesRes>> readPayments();

    @Operation(summary = "관리자 payment 상세 조회", description = "관리자 권한으로 fail_log를 조회한다")
    @ApiResponse(useReturnTypeSchema = true)
    @GetMapping("/admin/payments/{paymentId}")
    ResponseEntity<ResponseBody<FailLogRes>> readFailLog(
        @PathVariable(name = "paymentId") Long paymentId
    );
}
