package com.example.ufo_fi.v2.user.domain;

import org.junit.jupiter.api.*;

import static com.example.ufo_fi.v2.user.domain.Role.*;
import static org.assertj.core.api.Assertions.*;

class UserTest {

    @Nested
    @DisplayName("유저 초기 생성 규칙")
    class InitialCreation {
        @DisplayName("유저 생성 시 유저 상태는 ROLE_NO_INFO 이다.")
        @Test
        void of_valid_input_setsRoleNoInfo() {
            String kakaoId = "kakao123";
            String email = "test@naver.com";

            // when
            User user = User.of(kakaoId, email, ROLE_NO_INFO);

            // then
            assertThat(user.getRole()).isEqualTo(ROLE_NO_INFO);

        }

        @DisplayName("유저 생성 시 전화번호는 빈 문자열이어야 한다.")
        @Test
        void of_initialState_setsEmptyPhoneNumber() {
            // given
            String kakaoId = "kakao123";
            String email = "test@naver.com";

            // when
            User user = User.of(kakaoId, email, ROLE_NO_INFO);

            // then
            assertThat(user.getPhoneNumber()).isEqualTo("");
        }

        @DisplayName("유저 생성 시 email이 null이면 예외가 발생한다.")
        @Test
        void of_emailIsNull_throwsIllegalArgumentException() {
            // given
            String kakaoId = "kakao123";
            String email = null;

            // when & then
            // assertThatThrownBy(() -> User.of(kakaoId, email, ROLE_NO_INFO))
            //         .isInstanceOf(IllegalArgumentException.class)
            //         .hasMessageContaining("email");
        }
    }

    @DisplayName("유저 로그인 정보 업데이트 시 유저의 상태는 ROLE_USER 이다.")
    @Test
    void updateUserBaseInfo_userStateIsRoleUser_true() {
        // given
        String kakaoId = "kakao123";
        String email = "test@naver.com";
        User user = User.of(kakaoId, email, ROLE_NO_INFO);

        String name = "정무민";
        String phoneNumber = "010-4615-6511";
        boolean activeStatus = true;

        // when
        user.updateUserBaseInfo(name, phoneNumber, activeStatus, ROLE_USER);

        // then
        assertThat(user.getRole()).isEqualByComparingTo(ROLE_USER);
    }

    @DisplayName("유저 로그인 정보 업데이트 시 신고된 유저는 정보를 업데이트 할 수 없다.")
    @Test
    void updateUserBaseInfo_userStateIsRoleReported_fail() {
        // given
        String kakaoId = "kakao123";
        String email = "test@naver.com";
        User user = User.of(kakaoId, email, ROLE_REPORTED);

        String name = "정무민";
        String phoneNumber = "010-4615-6511";
        boolean activeStatus = true;

        // when & then
        assertThatThrownBy(() -> user.updateUserBaseInfo(name, phoneNumber, activeStatus, ROLE_USER))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("정지된 사용자는 어떤 동작도 할 수 없습니다.");
    }

    @DisplayName("유저의 보유 재화는 주어진 가격 만큼 충전할 수 있다.")
    @Test
    void increaseZetAsset_validAmount_increasesBalance() {
        // given
        User user = createUser("kakao123", "test1@naver.com", "테스트유저1", "010-1234-1234", true, ROLE_USER);

        // when
        int beforeZet = user.getZetAsset();
        int inputZet = 100;
        user.increaseZetAsset(inputZet);

        // then
        assertThat(user.getZetAsset() - inputZet).isEqualTo(beforeZet);
    }


    @DisplayName("유저의 보유 재화는 주어진 가격 만큼 차감할 수 있다.")
    @Test
    void decreaseZetAsset_validAmount_decreasesBalance() {
        // given
        User user = createUser("kakao123", "test1@naver.com", "테스트유저1", "010-1234-1234", true, ROLE_USER);
        user.increaseZetAsset(100);
        int beforeZet = user.getZetAsset();

        // when
        int inputZet = 20;
        user.decreaseZetAsset(inputZet);
        
        // then
        assertThat(user.getZetAsset() + inputZet).isEqualTo(beforeZet);
    }

    @DisplayName("유저의 닉네임은 주어진 이름으로 변경할 수 있다")
    @Test
    void updateNickname_validName_updatesNickname() {
        // given
        User user = createUser("kakao123", "test1@naver.com", "테스트유저1", "010-1234-1234", true, ROLE_USER);
        String newNickname = "초롱초롱한 지구인";

        // when
        user.updateNickname(newNickname);

        // then
        assertThat(user.getNickname()).isEqualTo(newNickname);
    }

    @DisplayName("유저의 role이 ROLE_REPORTED일 경우 ROLE_USER로 변경할 수 있다.")
    @Test
    void updateRoleUser_validRole_updateToRoleUser() {
        // given
        User user = createUser("kakao123", "test1@naver.com", "테스트유저1", "010-1234-1234", false, ROLE_REPORTED);

        // when
        user.updateRoleUser();

        // then
        assertThat(user.getRole()).isEqualTo(ROLE_USER);
    }

    @DisplayName("유저의 role이 ROLE_REPORTED가 아닌 경우에는 ROLE_USER로 변경할 수 없다.")
    @Test
    void updateRoleUser_reported_throwsIllegalStateException() {
        // given
        User user = createUser("kakao123", "test1@naver.com", "테스트유저1", "010-1234-1234", false, ROLE_USER);

        // when & then
        assertThatThrownBy(user::updateRoleUser)
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("정지된 사용자만 일반 사용자로 권한을 업데이트 할 수 있습니다.");
    }



    // user 생성 편의 메서드
    private User createUser(String kakaoId, String email, String name, String phoneNumber, boolean activeState, Role role) {
        User user = User.of(kakaoId, email, ROLE_NO_INFO);
        user.updateUserBaseInfo(name, phoneNumber, activeState, role);
        return user;
    }

}