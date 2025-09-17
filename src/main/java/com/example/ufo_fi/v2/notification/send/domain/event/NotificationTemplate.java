package com.example.ufo_fi.v2.notification.send.domain.event;

public enum NotificationTemplate {
    // 혜택 알림
    EVENT_BENEFIT(
            "새로운 혜택이 도착했어요!",
            "지금 바로 확인해보세요!",
            ""
    ),

    // 거래 완료 알림
    DATA_SELL(
            "데이터 판매 완료!",
            "UFO-Fi 판매 내역을 확인하세요.",
            "/mypage/trade"
    ),

    // 계정 정지 알림
    USER_BLOCK(
            "계정이 일시 정지되었습니다.",
            "자세한 사항은 UFO-Fi 정책을 확인해주세요.",
            ""
    ),

    // 관심 상품
    INTERESTED_PRODUCT(
            "관심 상품이 등록되었습니다.",
            "나에게 꼭 맞는 상품을 확인해보세요.",
            "/exchange"
    ),

    // 팔로워 새상품 등록 알림
    FOLLOW_PRODUCT(
            "팔로우한 지구인이 새 상품을 올렸어요!",
            "지금 UFO-FI에서 새로운 상품을 확인해보세요.",
            "/exchange"
    );

    private final String title;
    private final String bodyTemplate;
    private final String urlTemplate;

    NotificationTemplate(String title, String bodyTemplate, String urlTemplate) {
        this.title = title;
        this.bodyTemplate = bodyTemplate;
        this.urlTemplate = urlTemplate;
    }

    public String getTitle() {
        return title;
    }

    public String getBody(String... args) {
        return String.format(bodyTemplate, (Object[]) args);
    }

    public String getUrl(String... args) {
        return String.format(urlTemplate, (Object[]) args);
    }
}
