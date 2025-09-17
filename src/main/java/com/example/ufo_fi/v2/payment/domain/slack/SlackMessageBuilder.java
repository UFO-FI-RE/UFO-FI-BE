package com.example.ufo_fi.v2.payment.domain.slack;

import com.example.ufo_fi.v2.payment.domain.payment.MetaDataKey;
import com.example.ufo_fi.v2.payment.domain.payment.StateMetaData;
import com.example.ufo_fi.v2.payment.infrastructure.toss.response.ConfirmResult;
import com.example.ufo_fi.global.json.JsonUtil;
import com.example.ufo_fi.global.log.meta.PaymentLogTrace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SlackMessageBuilder {
    private final static String TOSS_REQUEST_NULL = "토스 승인 응답을 받지 못했습니다.";
    private final static String INTERNAL_SERVER_ERROR = "JSON 파싱 실패. 서버 점검이 필요합니다.";
    private final static String MESSAGE_TEMPLATE =
            """
            *[에러 메시지 알림입니다.]*
            ----------------------------------------- ;-)
            *사용자 ID*: %s
            *발생 시각*: %s
            - 토스 응답
            ```json
            %s
            ```
            <https://www.ufo-fi.store//admin/zet-recovery|관리자 페이지 바로가기>
            """;

    public String buildMessage(PaymentLogTrace logTrace, StateMetaData stateMetaData){
            return MESSAGE_TEMPLATE.formatted(
                    logTrace.getBasicLogInfo().getUserId(),
                    logTrace.getBasicLogInfo().getRequestAt(),
                    JsonUtil.toJson(stateMetaData.get(MetaDataKey.CONFIRM_RESULT, ConfirmResult.class))

            );
    }
}
