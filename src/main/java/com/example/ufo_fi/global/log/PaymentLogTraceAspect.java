package com.example.ufo_fi.global.log;

import com.example.ufo_fi.global.exception.GlobalException;
import com.example.ufo_fi.global.log.meta.BasicLogInfo;
import com.example.ufo_fi.global.log.meta.PaymentLogMethodTrace;
import com.example.ufo_fi.global.log.meta.PaymentLogTrace;
import com.example.ufo_fi.v2.auth.application.principal.DefaultUserPrincipal;
import com.example.ufo_fi.v2.payment.application.FailLogService;
import com.example.ufo_fi.v2.payment.domain.payment.MetaDataKey;
import com.example.ufo_fi.v2.payment.domain.payment.StateMetaData;
import com.example.ufo_fi.v2.payment.domain.payment.entity.FailLog;
import com.example.ufo_fi.v2.payment.infrastructure.toss.response.ConfirmResult;
import com.example.ufo_fi.v2.payment.persistence.FailLogRepository;
import com.example.ufo_fi.v2.payment.persistence.PaymentRepository;
import com.example.ufo_fi.v2.payment.presentation.dto.request.ConfirmReq;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class PaymentLogTraceAspect {

    private final FailLogService failLogService;

    @Pointcut("execution(* com.example.ufo_fi.v2.payment.presentation.PaymentController.confirm(..))")
    private void paymentLogTraceRequest() {}

    @Pointcut("""
        execution(* com.example.ufo_fi.v2.payment.domain.payment.state.ReadyState.proceed(..)) ||
        execution(* com.example.ufo_fi.v2.payment.domain.payment.state.InProgressState.proceed(..)) ||
        execution(* com.example.ufo_fi.v2.payment.domain.payment.state.FailState.proceed(..)) ||
        execution(* com.example.ufo_fi.v2.payment.domain.payment.state.DoneState.proceed(..)) ||
        execution(* com.example.ufo_fi.v2.payment.domain.payment.state.TimeoutState.proceed(..))
    """)
    private void paymentLogTraceStatus() {}

    //요청 시작, 요청 끝 basicLogInfo, ConfirmReq를 받아온다. + method Trace     state, 토스, 응답
    @Around("paymentLogTraceRequest()")
    public Object tracePaymentRequest(ProceedingJoinPoint joinPoint) throws Throwable {
        PaymentLogTrace logTrace = createLogTrace(joinPoint);
        PaymentLogTraceContext.set(logTrace);

        methodTrace(logTrace, joinPoint);

        try{
            return joinPoint.proceed();
        } catch (Throwable ex){
            failLogService.saveFailLog(FailLog.from(logTrace));
            throw ex;
        } finally {
            PaymentLogTraceContext.clear();
        }
    }

    //StateMetaData를 통해 토스의 응답값을 받아온다.
    @Around("paymentLogTraceStatus()")
    public Object tracePaymentStatus(ProceedingJoinPoint joinPoint) throws Throwable {
        PaymentLogTrace logTrace = PaymentLogTraceContext.get();

        methodTrace(logTrace, joinPoint);

        try{
            return joinPoint.proceed();
        } finally {
            extractConfirmResultFromStateMetaData(joinPoint, logTrace);
        }
    }

    private PaymentLogTrace createLogTrace(ProceedingJoinPoint joinPoint) {
        PaymentLogTrace paymentLogTrace = new PaymentLogTrace();
        addBasicInfo(paymentLogTrace);
        addConfirmReq(joinPoint, paymentLogTrace);
        return paymentLogTrace;
    }

    private void extractConfirmResultFromStateMetaData(
        ProceedingJoinPoint joinPoint,
        PaymentLogTrace logTrace
    ) {
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof StateMetaData metaData) {
                ConfirmResult confirmResult = metaData.get(
                    MetaDataKey.CONFIRM_RESULT,
                    ConfirmResult.class
                );
                if (confirmResult != null) {
                    logTrace.appendConfirmResult(confirmResult);
                }
            }
        }
    }

    private void methodTrace(PaymentLogTrace logTrace, ProceedingJoinPoint joinPoint) {
        logTrace.appendLogMethodTrace(joinPoint);

        PaymentLogMethodTrace logMethodTrace = logTrace.getLogMethodTrace();

        logMethodTrace.appendMethod(joinPoint.getSignature().toShortString());
    }

    private void addBasicInfo(PaymentLogTrace paymentLogTrace) {
        paymentLogTrace.appendBasicLogInfo(BasicLogInfo.of(
            extractUserIdFromSecurityContextHolder(),
            LocalDateTime.now()
        ));
    }

    private void addConfirmReq(ProceedingJoinPoint joinPoint, PaymentLogTrace paymentLogTrace) {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof ConfirmReq confirmReq) {
                paymentLogTrace.appendOrderId(confirmReq);
                paymentLogTrace.appendConfirmRequest(confirmReq);
            }
        }
    }

    private Long extractUserIdFromSecurityContextHolder() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof DefaultUserPrincipal userPrincipal) {
            return userPrincipal.getId();
        }
        return null;
    }
}