package com.example.ufo_fi.v2.payment.infrastructure.toss;

import com.example.ufo_fi.global.exception.GlobalException;
import com.example.ufo_fi.v2.payment.config.PaymentConfig;
import com.example.ufo_fi.v2.payment.exception.PaymentErrorCode;
import com.example.ufo_fi.v2.payment.infrastructure.toss.request.ConfirmCommand;
import com.example.ufo_fi.v2.payment.infrastructure.toss.response.ConfirmFailResult;
import com.example.ufo_fi.v2.payment.infrastructure.toss.response.ConfirmResult;
import com.example.ufo_fi.v2.payment.infrastructure.toss.response.ConfirmSuccessResult;
import com.example.ufo_fi.v2.payment.infrastructure.toss.response.ConfirmTimeoutResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * confirmCommand -> HttpRequest<String>
 * HttpResponse<String> -> ConfirmResult
 */
@Component
@RequiredArgsConstructor
public class TossClientUtil {
    private static final String AUTHORIZATION = "Authorization";
    private static final String BASIC = "Basic ";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String JSON = "application/json";
    private static final long TIME_OUT_LIMIT = 3;

    private final ObjectMapper objectMapper;
    private final PaymentConfig paymentConfig;

    public HttpRequest generateTossConfirmRequest(ConfirmCommand confirmCommand) {
        try {
            String rawConfirmCommand = objectMapper.writeValueAsString(confirmCommand);
            return createTossConfirmRequest(rawConfirmCommand);
        } catch (JsonProcessingException e) {
            throw new GlobalException(PaymentErrorCode.TOSS_PAYMENT_CONFIRM_PARSE_FAIL);
        }
    }

    public ConfirmResult generateTossConfirmResult(HttpResponse<String> tossConfirmHttpResponse) {
        String body = tossConfirmHttpResponse.body();

        try {
            JsonNode json = objectMapper.readTree(body);

            if(isOk(tossConfirmHttpResponse)){
                return ConfirmSuccessResult.from(json);
            }
            return ConfirmFailResult.from(json);
        } catch (JsonProcessingException e){
            throw new GlobalException(PaymentErrorCode.TOSS_PAYMENT_CONFIRM_PARSE_FAIL);
        }
    }

    public ConfirmResult generateTossTimeoutConfirmResult() {
        return new ConfirmTimeoutResult();
    }

    private HttpRequest createTossConfirmRequest(String rawConfirmCommand){
        return HttpRequest.newBuilder()
                .uri(URI.create(paymentConfig.getConfirmUrl()))
                .header(AUTHORIZATION, BASIC + paymentConfig.getPaymentSecretKey())
                .header(CONTENT_TYPE, JSON)
                .timeout(Duration.ofSeconds(TIME_OUT_LIMIT))
                .POST(HttpRequest.BodyPublishers.ofString(rawConfirmCommand))
                .build();
    }

    private boolean isOk(HttpResponse<String> tossConfirmHttpResponse){
        int code = tossConfirmHttpResponse.statusCode();
        return code >= 200 && code < 300;
    }
}
