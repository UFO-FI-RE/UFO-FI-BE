package com.example.ufo_fi.global.log.meta;

import com.example.ufo_fi.v2.payment.infrastructure.toss.response.ConfirmResult;
import com.example.ufo_fi.v2.payment.presentation.dto.request.ConfirmReq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentLogTrace {
    private String orderId;
    private BasicLogInfo basicLogInfo;
    private ConfirmReq confirmReq;
    private ConfirmResult confirmResult;
    private PaymentLogMethodTrace logMethodTrace;

    public void appendBasicLogInfo(BasicLogInfo basicLogInfo){
        this.basicLogInfo = basicLogInfo;
    }

    public void appendOrderId(ConfirmReq confirmReq) {
        this.orderId = confirmReq.getOrderId();
    }

    public void appendConfirmRequest(ConfirmReq confirmReq){
        this.confirmReq = confirmReq;
        this.orderId = confirmReq.getOrderId();
    }

    public void appendConfirmResult(Object result) {
        this.confirmResult = (ConfirmResult) result;
    }

    public void appendLogMethodTrace(ProceedingJoinPoint joinPoint){
        if(logMethodTrace == null){
            logMethodTrace = new PaymentLogMethodTrace();
        }
        logMethodTrace.appendMethod(joinPoint.getSignature().getName());
    }
}
