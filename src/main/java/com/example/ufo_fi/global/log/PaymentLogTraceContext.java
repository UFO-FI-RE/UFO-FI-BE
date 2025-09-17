package com.example.ufo_fi.global.log;

import com.example.ufo_fi.global.log.meta.PaymentLogTrace;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PaymentLogTraceContext {
    private static final ThreadLocal<PaymentLogTrace> LOG_TRACE_THREAD_LOCAL = new ThreadLocal<>();

    //LogTrace 가져오기
    public static PaymentLogTrace get() {
        return LOG_TRACE_THREAD_LOCAL.get();
    }

    //LogTrace 추가하기
    public static void set(PaymentLogTrace trace) {
        LOG_TRACE_THREAD_LOCAL.set(trace);
    }

    // 제거(메모리 효율성을 위해 반드시!!!!!!!! 호출해줍니다.)
    public static void clear() {
        LOG_TRACE_THREAD_LOCAL.remove();
    }
}
