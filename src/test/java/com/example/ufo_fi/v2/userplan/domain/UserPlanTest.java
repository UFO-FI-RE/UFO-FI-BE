package com.example.ufo_fi.v2.userplan.domain;

import com.example.ufo_fi.global.exception.GlobalException;
import com.example.ufo_fi.v2.plan.domain.MobileDataType;
import com.example.ufo_fi.v2.plan.domain.Plan;
import com.example.ufo_fi.v2.tradepost.exception.TradePostErrorCode;
import com.example.ufo_fi.v2.user.domain.Role;
import com.example.ufo_fi.v2.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.example.ufo_fi.v2.plan.domain.Carrier.*;
import static com.example.ufo_fi.v2.user.domain.Role.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class UserPlanTest {

    @DisplayName("유저 플랜 생성 시 구매한 데이터 용량은 0이다. ")
    @Test
    void of_initialUserPlan_purchaseDataAmountIsZero() {
        // given
        User user = createUser("kakao123", "test1@naver.com", "테스트유저1", "010-1234-1234", true, ROLE_USER);
        Plan plan = createPlan();

        // when
        UserPlan userPlan = UserPlan.of(plan, user, 10);

        // then
        assertThat(userPlan.getPurchaseDataAmount()).isZero();
    }

    // 정상 흐름 : 기존 판매량 만큼을 올려주고, 새로운 판매량을 다시 구한다
    @DisplayName("판매 가능한 데이터 용량 수정 시에는 기존 판매 용량 원복 후 새로운 용량 만큼 차감된다")
    @Test
    void updateSellableDataAmount_valid_updates() {
        // given
        User user = createUser("kakao123", "test1@naver.com", "테스트유저1", "010-1234-1234", true, ROLE_USER);
        Plan plan = createPlan();
        UserPlan userPlan = UserPlan.of(plan, user, 8);

        // when
        int originData = 2;
        int newData = 3;
        userPlan.updateSellableDataAmount(originData, newData);

        // then
        assertThat(userPlan.getSellableDataAmount()).isEqualTo(7);
    }

    // 증가 한도 초과 예외
    @DisplayName("판매 가능한 데이터 용량 수정 시에 기존 판매 용량을 원복한 값이 해당 요금제가 판매할 수 있는 용량을 초과하면 예외를 던진다")
    @Test
    void updateSellableDataAmount_whenRestoreExceedsCapacity_throwException() {
        // given
        User user = createUser("kakao123", "test1@naver.com", "테스트유저1", "010-1234-1234", true, ROLE_USER);
        Plan plan = createPlan();
        UserPlan userPlan = UserPlan.of(plan, user, 8);

        // when & then
        int originData = 10;
        int newData = 3;

        assertThatThrownBy(() -> userPlan.updateSellableDataAmount(originData, newData))
                .isInstanceOf(GlobalException.class)
                .hasFieldOrPropertyWithValue("errorCode", TradePostErrorCode.EXCEED_RESTORE_CAPACITY);

    }

    // 감소 한도 초과 예외
    @DisplayName("판매 가능한 데이터 용량 수정 시에 새로운 판매량을 차감한 값은 음수일 수 없다.")
    @Test
    void updateSellableDataAmount_whenSellExceedsHoldings_throwException() {
        // given
        User user = createUser("kakao123", "test1@naver.com", "테스트유저1", "010-1234-1234", true, ROLE_USER);
        Plan plan = createPlan();
        UserPlan userPlan = UserPlan.of(plan, user, 8);

        // when & then
        int originData = 2;
        int newData = 20;

        assertThatThrownBy(() -> userPlan.updateSellableDataAmount(originData, newData))
                .isInstanceOf(GlobalException.class)
                .hasFieldOrPropertyWithValue("errorCode", TradePostErrorCode.EXCEED_SELL_CAPACITY);
    }

    // 음수 입력 예외 (1)
    @DisplayName("판매 가능한 데이터 용량 수정 시에 기존 판매 용량은 음수일 수 없다.")
    @Test
    void updateSellableDataAmount_whenNegativeOriginData_throwException() {
        // given
        User user = createUser("kakao123", "test1@naver.com", "테스트유저1", "010-1234-1234", true, ROLE_USER);
        Plan plan = createPlan();
        UserPlan userPlan = UserPlan.of(plan, user, 8);

        // when & then
        int originData = -1;
        int newData = 3;

        // then
        assertThatThrownBy(() -> userPlan.updateSellableDataAmount(originData, newData))
                .isInstanceOf(GlobalException.class)
                .hasFieldOrPropertyWithValue("errorCode", TradePostErrorCode.EXCEED_RESTORE_CAPACITY);

    }

    // 음수 입력 예외 (2)
    @DisplayName("판매 가능한 데이터 용량 수정 시에 새로운 판매 용량은 음수일 수 없다.")
    @Test
    void updateSellableDataAmount_whenNegativeNewData_throwException() {
        // given
        User user = createUser("kakao123", "test1@naver.com", "테스트유저1", "010-1234-1234", true, ROLE_USER);
        Plan plan = createPlan();
        UserPlan userPlan = UserPlan.of(plan, user, 8);

        // when & then
        int originData = 2;
        int newData = -4;

        // then
        assertThatThrownBy(() -> userPlan.updateSellableDataAmount(originData, newData))
                .isInstanceOf(GlobalException.class)
                .hasFieldOrPropertyWithValue("errorCode", TradePostErrorCode.EXCEED_SELL_CAPACITY);
    }

    @DisplayName("구매한 데이터 용량은 입력된 용량 만큼 증가한다")
    @Test
    void increasePurchaseAmount_valid_increase() {
        // given
        User user = createUser("kakao123", "test1@naver.com", "테스트유저1", "010-1234-1234", true, ROLE_USER);
        Plan plan = createPlan();
        UserPlan userPlan = UserPlan.of(plan, user, 8);

        // when
        int purchaseDataAmount = 5;
        userPlan.increasePurchaseAmount(purchaseDataAmount);

        // then
        assertThat(userPlan.getPurchaseDataAmount()).isEqualTo(purchaseDataAmount);
    }

    @DisplayName("구매한 데이터 용량은 음수일 수 없다")
    @Test
    void increasePurchaseAmount_whenNegativePurchaseDataAmount_throwException() {
        // given
        User user = createUser("kakao123", "test1@naver.com", "테스트유저1", "010-1234-1234", true, ROLE_USER);
        Plan plan = createPlan();
        UserPlan userPlan = UserPlan.of(plan, user, 8);

        // when
        int purchaseDataAmount = -5;
        userPlan.increasePurchaseAmount(purchaseDataAmount);

        // then 추후 불변식 추가 시

    }



    // user 생성 편의 메서드
    private static User createUser(String kakaoId, String email, String name, String phoneNumber, boolean activeState, Role role) {
        User user = User.of(kakaoId, email, ROLE_NO_INFO);
        user.updateUserBaseInfo(name, phoneNumber, activeState, role);
        return user;
    }

    // plan 생성 편의 메서드
    private static Plan createPlan() {
        return Plan.builder()
                .name("무제한 10GB")
                .carrier(KT)
                .mobileDataAmount(null)
                .isUltimatedAmount(true)
                .sellMobileDataCapacityGb(10)
                .mobileDataType(MobileDataType._5G)
                .build();
    }


}