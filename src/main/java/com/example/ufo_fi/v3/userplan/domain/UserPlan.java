package com.example.ufo_fi.v3.userplan.domain;

import com.example.ufo_fi.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_plans")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserPlan extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Embedded
    private Data data;

    @Column(name = "plan_id")
    private Long planId;

    @Column(name = "user_id")
    private Long userId;

    @Builder(access = AccessLevel.PRIVATE)
    private UserPlan(Data data, Long planId, Long userId) {
        this.data = requireData(data);
        this.planId = requirePlanId(planId);
        this.userId = requireUserId(userId);
    }

    public static UserPlan of(Integer sellableData, Integer purchaseData, Long planId, Long userId) {
        return UserPlan.builder()
                .data(Data.of(sellableData, purchaseData))
                .planId(planId)
                .userId(userId)
                .build();
    }

    private Data requireData(Data data) {
        if(data == null) throw new IllegalArgumentException("data는 null일 수 없습니다.");
        return data;
    }

    private Long requirePlanId(Long planId) {
        if(planId == null) throw new IllegalArgumentException("planId는 null일 수 없습니다.");
        return planId;
    }

    private Long requireUserId(Long userId) {
        if(userId == null) throw new IllegalArgumentException("userId는 null일 수 없습니다.");
        return userId;
    }
}
