package com.example.ufo_fi.v2.auth.application;

import com.example.ufo_fi.v2.auth.domain.OAuthUserInfo;
import com.example.ufo_fi.v2.auth.domain.kakao.KakaoAccount;
import com.example.ufo_fi.v2.auth.domain.kakao.KakaoUserInfo;
import com.example.ufo_fi.v2.notification.send.application.WebPushClient;
import com.example.ufo_fi.v2.user.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@ActiveProfiles("test")
@SpringBootTest
class AuthServiceTest {

//    @Autowired
//    private AuthService authService;
//
//    @DisplayName("로그인 시 User가 저장됨을 확인할 수 있다.")
//    @Test
//    public void loginTest() {
//        //given
//        KakaoAccount kakaoAccount = new KakaoAccount("test2000@naver.com");
//        OAuthUserInfo oAuthUserInfo = new KakaoUserInfo("test-providerId", kakaoAccount);
//
//        //when
//        User user = authService.login(oAuthUserInfo);
//
//        //then
//        Assertions.assertThat(user)
//                .extracting("kakaoId", "email")
//                .contains("kakaotest-providerId", "test2000@naver.com");
//    }
}