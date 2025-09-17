package com.example.ufo_fi.v2.auth.application.oauth.provider.kakao;

import com.example.ufo_fi.v2.auth.application.oauth.provider.OAuth2Response;
import java.util.Map;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.oauth2.core.user.OAuth2User;

/**
 * {
 *   "id": 123456789,
 *   "connected_at": "2023-07-02T09:24:32Z",
 *   "properties": {
 *     "nickname": "홍길동"
 *   },
 *   "kakao_account": {
 *     "profile_nickname_needs_agreement": false,
 *     "profile": {
 *       "nickname": "홍길동"
 *     },
 *     "has_email": true,
 *     "email_needs_agreement": false,
 *     "is_email_valid": true,
 *     "is_email_verified": true,
 *     "email": "hong@kakao.com"
 *   }
 * }
 *
 * 위와 같은 값으로 가져온다.
 * 저 값들은 모든 OAuth client마다 다른데 이들을 하나로 묶기 위해 OAuth2Response를 상속했다.
 */

public class KakaoResponse extends OAuth2Response {
    public KakaoResponse(OAuth2User oAuth2User) {
        super(oAuth2User);
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public Long getProviderId() {
        if(super.oauth2User.getAttribute(Scope.KAKAO_ID.getScopeKey()) == null){
            throw new AuthenticationServiceException("provider id가 없음");
        }
        return (Long) super.oauth2User.getAttributes().get(Scope.KAKAO_ID.getScopeKey());
    }

    @Override
    public String getEmail() {
        Map<String, Object> attributes = super.oauth2User.getAttributes();
        if(attributes == null) throw new AuthenticationServiceException("email이 없음");
        Map<String, Object> kakaoAccount =
            (Map<String, Object>) attributes.get(Scope.KAKAO_ACCOUNT.getScopeKey());

        return (String) kakaoAccount.get(Scope.KAKAO_EMAIL.getScopeKey());
    }
}
