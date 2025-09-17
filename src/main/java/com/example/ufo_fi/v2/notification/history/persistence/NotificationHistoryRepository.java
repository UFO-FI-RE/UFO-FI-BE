package com.example.ufo_fi.v2.notification.history.persistence;

import com.example.ufo_fi.v2.notification.history.domain.NotificationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationHistoryRepository extends JpaRepository<NotificationHistory, Long> {

    List<NotificationHistory> findTop10ByUserIdOrderByNotifiedAtDesc(Long userId);
}
