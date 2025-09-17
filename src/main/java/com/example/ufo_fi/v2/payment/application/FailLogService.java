package com.example.ufo_fi.v2.payment.application;

import com.example.ufo_fi.v2.payment.domain.payment.entity.FailLog;
import com.example.ufo_fi.v2.payment.persistence.FailLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FailLogService {

    private final FailLogRepository failLogRepository;

    @Transactional
    public void saveFailLog(FailLog failLog){
        failLogRepository.save(failLog);
    }
}
