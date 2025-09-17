package com.example.ufo_fi.v2.notification.setting.presentation.api;

import com.example.ufo_fi.global.response.ResponseBody;
import com.example.ufo_fi.v2.notification.common.NotificationType;
import com.example.ufo_fi.v2.notification.setting.presentation.dto.response.NotificationSettingReadRes;
import com.example.ufo_fi.v2.auth.application.principal.DefaultUserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "NotificationSetting API", description = "알림 설정 API")
public interface NotificationSettingApiSpec {
    @Operation(summary = "알림 목록 조회 API", description = "내 알림 설정 목록을 조회한다.")
    @ApiResponse(useReturnTypeSchema = true)
    @GetMapping("/v1/mypage/notification-settings")
    ResponseEntity<ResponseBody<NotificationSettingReadRes>> readNotificationSettings(
            @AuthenticationPrincipal DefaultUserPrincipal defaultUserPrincipal
    );

    @Operation(summary = "Notification ON/OFF API", description = "내 알림을 활성/비활성화 한다.")
    @ApiResponse(useReturnTypeSchema = true)
    @PatchMapping("/v1/mypage/notification-settings")
    ResponseEntity<ResponseBody<Void>> updateNotificationSettings(
            @AuthenticationPrincipal DefaultUserPrincipal defaultUserPrincipal,
            @RequestParam NotificationType type,
            @RequestParam boolean enable);
}
