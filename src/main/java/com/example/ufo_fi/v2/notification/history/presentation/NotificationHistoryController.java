package com.example.ufo_fi.v2.notification.history.presentation;

import com.example.ufo_fi.global.response.ResponseBody;
import com.example.ufo_fi.v2.notification.history.applicaton.NotificationHistoryService;
import com.example.ufo_fi.v2.notification.history.presentation.api.NotificationHistoryApiSpec;
import com.example.ufo_fi.v2.notification.history.presentation.dto.response.NotificationListRes;
import com.example.ufo_fi.v2.auth.application.principal.DefaultUserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NotificationHistoryController implements NotificationHistoryApiSpec {

    private final NotificationHistoryService notificationService;

    @Override
    public ResponseEntity<ResponseBody<NotificationListRes>> readNotifications(
            @AuthenticationPrincipal DefaultUserPrincipal defaultUserPrincipal
    ) {
        return ResponseEntity.ok(ResponseBody.success(notificationService.readNotifications(defaultUserPrincipal.getId())));
    }
}
