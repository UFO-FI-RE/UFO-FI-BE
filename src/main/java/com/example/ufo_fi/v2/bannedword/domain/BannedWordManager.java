package com.example.ufo_fi.v2.bannedword.domain;

import com.example.ufo_fi.global.exception.GlobalException;
import com.example.ufo_fi.v2.bannedword.exception.BannedWordErrorCode;
import com.example.ufo_fi.v2.bannedword.persistence.BannedWordRepository;
import com.example.ufo_fi.v2.bannedword.presentation.dto.response.BannedWordReadRes;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BannedWordManager {

    private final BannedWordRepository bannedWordRepository;

    public BannedWord saveBannedWord(BannedWord bannedWord) {

        return bannedWordRepository.save(bannedWord);
    }

    public void validateWordNotExists(String word) {

        if (bannedWordRepository.existsByWord(word)) {
            throw new GlobalException(BannedWordErrorCode.DUPLICATED_BANNED_WORD);
        }
    }

    public void validateAllIdsExist(List<Long> ids, List<BannedWord> entities,
        BannedWordErrorCode errorCode) {

        Set<Long> foundIds = entities.stream().map(BannedWord::getId).collect(Collectors.toSet());

        if (!foundIds.containsAll(ids)) {
            throw new GlobalException(errorCode);
        }
    }

    public BannedWord getBannedWord(Long id) {
        return bannedWordRepository.findById(id)
            .orElseThrow(() -> new GlobalException(BannedWordErrorCode.BANNED_WORD_NOT_FOUND));
    }

    public Page<BannedWordReadRes> readBannedWordsAndPage(Pageable pageable) {

        return bannedWordRepository.findAll(pageable).map(BannedWordReadRes::from);
    }

    public void deleteBannedWord(BannedWord bannedWord) {
        bannedWordRepository.delete(bannedWord);
    }

    public List<BannedWord> findBannedWordsById(List<Long> ids) {

        return bannedWordRepository.findAllById(ids);
    }

    public void deleteAllBannedWord(List<BannedWord> bannedWords) {

        bannedWordRepository.deleteAllInBatch(bannedWords);
    }
}
