package com.example.ufo_fi.v2.notification.send.persistence;

import com.example.ufo_fi.v2.notification.send.domain.FcmToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FcmTokenRepository extends JpaRepository<FcmToken, Long> {
    @Query("SELECT f.fcm FROM FcmToken f WHERE f.user.id IN :userIds")
    List<String> findTokensByUserIds(@Param("userIds") List<Long> userIds);

    @Query("SELECT f.fcm FROM FcmToken f WHERE f.user.id = :userId")
    Optional<String> findByUserId(@Param("userId") Long userId);
}
