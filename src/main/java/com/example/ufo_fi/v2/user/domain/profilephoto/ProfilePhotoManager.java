package com.example.ufo_fi.v2.user.domain.profilephoto;

import com.example.ufo_fi.v2.user.exception.UserErrorCode;
import com.example.ufo_fi.v2.user.persistence.ProfilePhotoRepository;
import com.example.ufo_fi.global.exception.GlobalException;
import com.example.ufo_fi.v2.user.domain.nickname.AutoIncrementRandomIdSelector;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProfilePhotoManager {

    private final ProfilePhotoRepository profilePhotoRepository;
    private final AutoIncrementRandomIdSelector autoIncrementRandomIdSelector;

    public ProfilePhoto selectRandomPhoto(){
        long photoCount = profilePhotoRepository.count();
        long randomId = autoIncrementRandomIdSelector.select(photoCount);

        return profilePhotoRepository.findById(randomId)
            .orElseThrow(() -> new GlobalException(UserErrorCode.NOT_FOUND_PROFILE_PHOTO));
    }
}
