package com.example.ufo_fi.v2.payment.domain.payment.state;

import com.example.ufo_fi.global.log.PaymentLogTraceContext;
import com.example.ufo_fi.v2.payment.domain.payment.MetaDataKey;
import com.example.ufo_fi.v2.payment.domain.payment.entity.Payment;
import com.example.ufo_fi.v2.payment.domain.payment.PaymentManager;
import com.example.ufo_fi.v2.payment.domain.payment.PaymentStatus;
import com.example.ufo_fi.v2.payment.domain.payment.StateMetaData;
import com.example.ufo_fi.v2.payment.domain.payment.state.failstrategy.TossErrorCode;
import com.example.ufo_fi.v2.payment.domain.payment.state.failstrategy.TossErrorHandleStrategyContext;
import com.example.ufo_fi.v2.payment.domain.slack.SlackEvent;
import com.example.ufo_fi.v2.payment.infrastructure.toss.response.ConfirmFailResult;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * 토스에서 응답이 왔지만 실패한 경우이다.
 * 1. "PROVIDER_ERROR"
 * => 일시적 오류 : 재시도 카운트 한 번 하고 또 에러 시 슬랙에 알림
 * 2. "CARD_PROCESSING_ERROR"
 * => 카드사 오류 : 재시도 카운트 한 번 하고 또 에러 시 슬랙에 알림
 * 3. "FAILED_PAYMENT_INTERNAL_SYSTEM_PROCESSING"
 * => 토스사 에러 : 재시도 카운트 한 번 하고 또 에러 시 슬랙에 알림
 * 4. "UNKNOWN_PAYMENT_ERROR"
 * => 알 수 없는 오류 : 재시도 카운트 한 번 하고 또 에러 시 슬랙에 알림
 * 5. "FDS_ERROR"
 * => 위험 거래 감지
 * 해당 계정 토큰 뺏고, 계정 정지 슬랙으로 관리자에게 바로 알리기
 */
@Component
@RequiredArgsConstructor
public class FailState implements State {
    private static final Integer MAX_RETRY_COUNT = 2;

    private final PaymentManager paymentManager;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final TossErrorHandleStrategyContext tossErrorHandleStrategyContext;

    @Override
    public void proceed(Payment payment, StateMetaData stateMetaData) {
        verifyStatus(payment, PaymentStatus.FAIL);

        ConfirmFailResult confirmFailResult = stateMetaData.get(MetaDataKey.CONFIRM_RESULT,
                ConfirmFailResult.class);
        String tossConfirmErrorCode = confirmFailResult.getCode();

        updateByErrorCode(payment, tossConfirmErrorCode, stateMetaData);
    }

    @Override
    public PaymentStatus status() {
        return PaymentStatus.FAIL;
    }

    private void updateByErrorCode(Payment payment, String tossConfirmErrorCode, StateMetaData stateMetaData) {
        tossErrorHandleStrategyContext.process(payment, tossConfirmErrorCode, stateMetaData);
    }
}
