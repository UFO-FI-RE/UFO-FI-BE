package com.example.ufo_fi.v2.auth.application.oauth;


import com.example.ufo_fi.v2.auth.domain.Refresh;
import com.example.ufo_fi.v2.auth.domain.RefreshManager;
import com.example.ufo_fi.v2.interestedpost.domain.InterestedPost;
import com.example.ufo_fi.v2.interestedpost.persistence.InterestedPostRepository;
import com.example.ufo_fi.v2.notification.setting.domain.NotificationSetting;
import com.example.ufo_fi.v2.notification.setting.persistence.NotificationSettingRepository;
import com.example.ufo_fi.v2.user.domain.Role;
import com.example.ufo_fi.v2.user.domain.User;
import com.example.ufo_fi.v2.user.domain.UserManager;
import com.example.ufo_fi.v2.auth.application.oauth.provider.OAuth2Response;
import com.example.ufo_fi.v2.auth.application.principal.CustomOAuth2User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserManager userManager;
    private final RefreshManager refreshManager;
    private final InterestedPostRepository interestedPostRepository;
    private final NotificationSettingRepository notificationSettingRepository;

    /**
     * 1. DefaultOAuth2User를 받아옴(DefaultOAuth2UserService에서 Access 토큰을 받아오고, 그것으로 카카오 유저 서비스에 접근해 정보 또한 받아온다.)
     * 2. OAuth2Response => 모든 OAuth 인증 추상 객체
     * 3. kakao 계정 id로 User를 찾아온다.
     * 4. 분기
     * 4-1. 만약 유저가 존재한다면, 로그인한다.
     * 4-2. 만약 유저가 존재하지 않는다면, 회원가입을 한다.
     */
    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest); //1
        OAuth2Response oAuth2Response = OAuth2Response.of(userRequest.getClientRegistration(), oAuth2User);  //2
        User existUser = userManager.findByKakaoId(oAuth2Response.getProviderId().toString());  //3
        if(existUser != null) return login(existUser);  //4-1
        return createUser(oAuth2Response);  //4-2
    }

    /**
     * 로그인한다.
     * 1. refresh 토큰이 있다면 제거한다.
     * 2. OAuth2User를 생성한다.(후에 OAuth2Provider에서 OAuth2AuthenticationToken에 들어간다.
     * ** OAuth2로 로그인할 경우 CustomOAuth2User를 @AuthenticationPrincipal로 사용가능하다.
     */
    private OAuth2User login(User existUser) {
        Refresh refresh = existUser.getRefresh();
        if(refresh != null){
            existUser.registerRefresh(null);
            refreshManager.delete(refresh);
        }

        return CustomOAuth2User.from(existUser);
    }

    /*
     * 회원가입한다.
     * 1. 첫 회원가입은 ROLE_NO_INFO로 한다.
     * 2. 저장 후 OAuth2User를 생성 반환한다.
     */
    private OAuth2User createUser(OAuth2Response oAuth2Response) {
        User newUser = User.of(oAuth2Response, Role.ROLE_NO_INFO, 0);
        User signupUser = userManager.save(newUser);

        NotificationSetting notificationSetting = NotificationSetting.from(
                newUser,
                false,
                false,
                false,
                false,
                false
        );
        notificationSettingRepository.save(notificationSetting);

        InterestedPost interestedPost = InterestedPost.from(newUser);
        interestedPostRepository.save(interestedPost);

        return CustomOAuth2User.from(signupUser);
    }
}
