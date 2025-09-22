package com.example.ufo_fi.v2.notification.setting.presentation;

import com.example.ufo_fi.global.response.ResponseBody;
import com.example.ufo_fi.v2.auth.presentation.AuthContextHolder;
import com.example.ufo_fi.v2.notification.common.NotificationType;
import com.example.ufo_fi.v2.notification.setting.application.NotificationSettingService;
import com.example.ufo_fi.v2.notification.setting.presentation.api.NotificationSettingApiSpec;
import com.example.ufo_fi.v2.notification.setting.presentation.dto.response.NotificationSettingReadRes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NotificationSettingController implements NotificationSettingApiSpec {

    private final NotificationSettingService notificationService;

    // GetMapping, 알림 설정 목록 Read
    @Override
    public ResponseEntity<ResponseBody<NotificationSettingReadRes>> readNotificationSettings() {
        return ResponseEntity.ok(
                ResponseBody.success(
                        notificationService.readNotificationSettings(AuthContextHolder.getUserId())));
    }

    // TODO 유효하지 않은 알림타입 들어왔을 경우 예외 처리
    @Override
    public ResponseEntity<ResponseBody<Void>> updateNotificationSettings(
            NotificationType type,
            boolean enable
    ) {
        notificationService.updateNotificationSettings(AuthContextHolder.getUserId(), type, enable);
        return ResponseEntity.ok(ResponseBody.noContent());
    }
}
