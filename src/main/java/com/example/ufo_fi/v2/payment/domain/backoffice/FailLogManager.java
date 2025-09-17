package com.example.ufo_fi.v2.payment.domain.backoffice;

import com.example.ufo_fi.global.exception.GlobalException;
import com.example.ufo_fi.v2.payment.domain.payment.entity.FailLog;
import com.example.ufo_fi.v2.payment.exception.PaymentErrorCode;
import com.example.ufo_fi.v2.payment.persistence.FailLogRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FailLogManager {

    private final FailLogRepository failLogRepository;

    public List<FailLog> findAll() {
        return failLogRepository.findAll();
    }

    public FailLog findByOrderId(String orderId) {
        return failLogRepository.findByOrderId(orderId)
            .orElseThrow(() -> new GlobalException(PaymentErrorCode.FAIL_LOG_NOT_FOUND));
    }
}
