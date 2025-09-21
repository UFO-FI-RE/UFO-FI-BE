package com.example.ufo_fi.v2.auth.application;

import com.example.ufo_fi.v2.auth.domain.OAuthAccount;
import com.example.ufo_fi.v2.auth.domain.OAuthUserInfo;
import com.example.ufo_fi.v2.user.domain.User;
import com.example.ufo_fi.v2.user.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;

    @Transactional
    public User login(OAuthUserInfo oAuthUserInfo) {
        User user = userRepository.findByKakaoId(oAuthUserInfo.getId());

        if(user == null) user = signup(oAuthUserInfo);
        return user;
    }

    private User signup(OAuthUserInfo oAuthUserInfo) {
        OAuthAccount oAuthAccount = oAuthUserInfo.getOAuthAccount();
        User user = User.of(oAuthUserInfo.getId(), oAuthAccount.getEmail());
        return userRepository.save(user);
    }
}
