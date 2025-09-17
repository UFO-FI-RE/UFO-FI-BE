package com.example.ufo_fi.v2.notification.setting.presentation.dto.response;

import com.example.ufo_fi.v2.notification.setting.domain.NotificationSetting;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationSettingReadRes {
    @Schema(description = "혜택 알림 수신 여부", example = "true")
    private boolean benefit;

    @Schema(description = "판매 알림 수신 여부", example = "true")
    private boolean sell;

    @Schema(description = "관심 상품 알림 수신 여부", example = "true")
    private boolean interestedPost;

    @Schema(description = "신고 누적 알림 수신 여부", example = "true")
    private boolean reported;

    @Schema(description = "팔로우한 유저의 게시글 업로드 알림 수신 여부", example = "true")
    private boolean followerPost;

    @Schema(description = "거래 전체 알림", example = "true")
    private boolean tradeAll;

    public static NotificationSettingReadRes from(NotificationSetting settings, boolean isTradeAll) {
        return NotificationSettingReadRes.builder()
                .benefit(settings.getIsEventAgreed())
                .sell(settings.getIsSellAgreed())
                .interestedPost(settings.getIsInterestedPostAgreed())
                .reported(settings.getIsReportedAgreed())
                .followerPost(settings.getIsFollowerPostAgreed())
                .tradeAll(isTradeAll)
                .build();
    }
}
