package com.example.ufo_fi.v2.payment.domain.payment.state.failstrategy;

import com.example.ufo_fi.global.exception.GlobalException;
import com.example.ufo_fi.v2.auth.application.jwt.JwtUtil;
import com.example.ufo_fi.v2.auth.application.oauth.CookieUtil;
import com.example.ufo_fi.v2.payment.domain.payment.MetaDataKey;
import com.example.ufo_fi.v2.payment.domain.payment.StateMetaData;
import com.example.ufo_fi.v2.payment.domain.payment.entity.Payment;
import com.example.ufo_fi.v2.user.domain.Role;
import com.example.ufo_fi.v2.user.domain.User;
import com.example.ufo_fi.v2.user.domain.UserManager;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@RequiredArgsConstructor
public class ReportedUserStrategy implements TossErrorHandleStrategy {

    private final JwtUtil jwtUtil;
    private final UserManager userManager;

    //User의 Role을 업데이트하고, JWT 토큰을 삭제합니다.
    @Override
    @Transactional
    public void process(Payment payment, TossErrorCode tossErrorCode, StateMetaData stateMetaData) {
        ServletRequestAttributes attributes =
            (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(attributes != null){
            HttpServletResponse response = attributes.getResponse();
            if(response != null){
                jwtUtil.deleteJwtCookie(response);
            }
        }
        User user = userManager.findById(payment.getUser().getId());
        userManager.updateUserRole(user, Role.ROLE_REPORTED);
        stateMetaData.put(MetaDataKey.PAYMENT_DONE, true);
        throw new GlobalException(tossErrorCode);
    }

    @Override
    public TossErrorStrategy tossErrorStrategy() {
        return TossErrorStrategy.REPORTED_USER;
    }
}
