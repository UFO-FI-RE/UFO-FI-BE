package com.example.ufo_fi.v2.user.domain;

import com.example.ufo_fi.global.exception.GlobalException;
import com.example.ufo_fi.v2.tradepost.exception.TradePostErrorCode;
import com.example.ufo_fi.v2.user.domain.profilephoto.ProfilePhoto;
import com.example.ufo_fi.v2.user.exception.UserErrorCode;
import com.example.ufo_fi.v2.user.persistence.UserRepository;
import com.example.ufo_fi.v2.userplan.presentation.dto.request.UserInfoReq;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserManager {

    private final UserRepository userRepository;

    public User validateUserExistence(Long userId) {

        return userRepository.findById(userId)
                .orElseThrow(() -> new GlobalException(TradePostErrorCode.USER_NOT_FOUND));
    }

    public void validateUserRole(User user, Role role) {

        if (user.getRole() != role) {
            throw new GlobalException(UserErrorCode.INVALID_ROLE);
        }
    }

    public User findById(Long userId) {

        return userRepository.findById(userId)
                .orElseThrow(() -> new GlobalException(UserErrorCode.NOT_FOUND_USER));
    }

    public void updateUserNickname(
            User user, UserInfoReq userInfoReq, String randomNickname, ProfilePhoto randomProfilePhoto
    ) {
        user.signup(userInfoReq, randomNickname, randomProfilePhoto, true, Role.ROLE_USER);
    }

    public String getPhoneNumber(User user) {
        return user.getPhoneNumber() != null ? user.getPhoneNumber() : "";
    }

    public void updateUserNickname(User user, String nickname, Long userId) {
        user.updateNickname(nickname + String.format(" #%03d", userId));
    }

    public void updateUserRole(User user, Role role) {
        user.updateRole(role);
    }

    public Page<User> findAllByRole(Role role, PageRequest pageRequest) {

        return userRepository.findAllByRole(role, pageRequest);
    }

    public User findByKakaoId(String string) {
        return userRepository.findByKakaoId(string);
    }

    public User save(User newUser) {
        return userRepository.save(newUser);
    }

    public void decreaseZetAsset(User buyer, Integer totalZet) {
        buyer.decreaseZetAsset(totalZet);
    }

    public void increaseZetAsset(User seller, Integer totalZet) {
        seller.increaseZetAsset(totalZet);
    }

    public void validateUserPlanZetRemain(User buyer, Integer totalZet) {
        if (buyer.getZetAsset() < totalZet) {
            throw new GlobalException(UserErrorCode.LACK_ZET);
        }
    }

    public void validateMyselfPurchase(Long userId, Long sellerId) {
        if (userId.equals(sellerId)) {
            throw new GlobalException(UserErrorCode.CANT_PURCHASE_MYSELF);
        }
    }

    public void zetRecovery(User user, Integer zet) {
        user.increaseZetAsset(zet);
    }
}
