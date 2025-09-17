package com.example.ufo_fi.v2.notification.setting.persistence;

import com.example.ufo_fi.v2.notification.setting.domain.NotificationSetting;
import com.example.ufo_fi.v2.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NotificationSettingRepository extends JpaRepository<NotificationSetting, Long> {
    Optional<NotificationSetting> findByUser(User user);

    // 혜택 알림 수신을 동의한 모든 사용자
    @Query("SELECT ns.user.id FROM NotificationSetting ns WHERE ns.isEventAgreed = true")
    List<Long> findUserIdsWithBenefitAgreed();
}
