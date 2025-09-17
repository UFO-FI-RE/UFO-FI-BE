package com.example.ufo_fi.v2.payment.infrastructure.toss;

import com.example.ufo_fi.global.exception.GlobalException;
import com.example.ufo_fi.v2.payment.application.PaymentClient;
import com.example.ufo_fi.v2.payment.exception.PaymentErrorCode;
import com.example.ufo_fi.v2.payment.infrastructure.toss.request.ConfirmCommand;
import com.example.ufo_fi.v2.payment.infrastructure.toss.response.ConfirmResult;
import java.net.http.HttpTimeoutException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


@Slf4j
@Component
@RequiredArgsConstructor
public class TossPaymentClient implements PaymentClient {
    private final TossClientUtil tossClientUtil;

    /**
     * 결제 최종 승인 요청
     */
    @Override
    public ConfirmResult confirmPayment(ConfirmCommand confirmCommand) {
        HttpRequest tossConfirmRequest = tossClientUtil.generateTossConfirmRequest(confirmCommand);
        try {
            HttpResponse<String> tossConfirmHttpResponse = confirm(tossConfirmRequest);
            return tossClientUtil.generateTossConfirmResult(tossConfirmHttpResponse);
        } catch (IOException | InterruptedException e) {
            if(e instanceof HttpTimeoutException) {
                return tossClientUtil.generateTossTimeoutConfirmResult();
            }
            throw new GlobalException(PaymentErrorCode.CONFIRM_ERROR);
        }
    }

    private HttpResponse<String> confirm(HttpRequest tossConfirmRequest)
            throws IOException, InterruptedException {

        return HttpClient.newHttpClient().send(
                    tossConfirmRequest,
                    HttpResponse.BodyHandlers.ofString()
        );
    }
}
