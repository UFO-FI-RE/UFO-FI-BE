package com.example.ufo_fi.v2.payment.domain.payment.state.failstrategy;

public enum TossErrorStrategy {
    RETRIABLE,
    USER_FIXABLE,
    LIMIT_EXCEEDED,
    SERVER_CONFIG_ERROR,
    REPORTED_USER,
    IMMEDIATE_FAIL
}
