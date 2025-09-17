package com.example.ufo_fi.v2.notification.history.presentation.api;

import com.example.ufo_fi.global.response.ResponseBody;
import com.example.ufo_fi.v2.notification.history.presentation.dto.response.NotificationListRes;
import com.example.ufo_fi.v2.auth.application.principal.DefaultUserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;

@Tag(name = "Notification API", description = "알림 API")
public interface NotificationHistoryApiSpec {

    @Operation(summary = "최근 알림 내역 조회 API", description = "가장 최근에 발행된 알림 내역을 조회합니다.")
    @ApiResponse(useReturnTypeSchema = true)
    @GetMapping("/v1/notifications")
    ResponseEntity<ResponseBody<NotificationListRes>> readNotifications(
            @AuthenticationPrincipal DefaultUserPrincipal defaultUserPrincipal
    );
}
