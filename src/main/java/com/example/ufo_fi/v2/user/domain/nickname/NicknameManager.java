package com.example.ufo_fi.v2.user.domain.nickname;

import com.example.ufo_fi.v2.user.exception.UserErrorCode;
import com.example.ufo_fi.v2.user.persistence.NicknameRepository;
import com.example.ufo_fi.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NicknameManager {
    private static final String ID_FORMAT = "%03d";
    private static final String BASIC_NAME = " 지구인 #";

    private final NicknameRepository nicknameRepository;
    private final AutoIncrementRandomIdSelector autoIncrementRandomIdSelector;

    public String generateNickname() {
        long nicknameCount = nicknameRepository.count();
        long randomId = autoIncrementRandomIdSelector.select(nicknameCount);
        return generateFullNickname(randomId);
    }

    private String generateFullNickname(long randomId) {
        Nickname nickname = nicknameRepository.findById(randomId)
            .orElseThrow(() -> new GlobalException(UserErrorCode.NOT_FOUND_NICKNAME));

        String rawNickname = nickname.getNicknameAdjective();
        String formattedId = String.format(ID_FORMAT, randomId);

        return rawNickname + BASIC_NAME + formattedId;
    }
}
