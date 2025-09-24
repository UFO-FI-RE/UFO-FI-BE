package com.example.ufo_fi.v2.bannedword.application;


import com.example.ufo_fi.global.exception.GlobalException;
import com.example.ufo_fi.v2.bannedword.domain.BannedWord;
import com.example.ufo_fi.v2.bannedword.domain.BannedWordManager;
import com.example.ufo_fi.v2.bannedword.domain.filter.BannedWordFilter;
import com.example.ufo_fi.v2.bannedword.exception.BannedWordErrorCode;
import com.example.ufo_fi.v2.bannedword.persistence.BannedWordRepository;
import com.example.ufo_fi.v2.bannedword.presentation.dto.request.BannedWordCreateReq;
import com.example.ufo_fi.v2.bannedword.presentation.dto.request.BannedWordDeleteBulkReq;
import com.example.ufo_fi.v2.bannedword.presentation.dto.request.BannedWordReadPageReq;
import com.example.ufo_fi.v2.bannedword.presentation.dto.response.BannedWordBulkDeleteRes;
import com.example.ufo_fi.v2.bannedword.presentation.dto.response.BannedWordCreateRes;
import com.example.ufo_fi.v2.bannedword.presentation.dto.response.BannedWordDeleteRes;
import com.example.ufo_fi.v2.bannedword.presentation.dto.response.BannedWordReadRes;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class BannedWordService {

    private final BannedWordMapper bannedWordMapper;
    private final BannedWordManager bannedWordManager;

    private final BannedWordFilter bannedWordFilter;
    private final BannedWordRepository bannedWordRepository;

    @Transactional
    public BannedWordCreateRes createBannedWord(BannedWordCreateReq request) {

        // before
        // BannedWord bannedWord = bannedWordMapper.toBannedWord(request);
        // bannedWordManager.validateWordNotExists(request.getBanWord());

        // BannedWord savedBannedWord = bannedWordManager.saveBannedWord(bannedWord);

        validateWordExists(request);

        BannedWord bannedWord = BannedWord.from(request.getBanWord());
        BannedWord savedBannedWord = bannedWordRepository.save(bannedWord);

        bannedWordFilter.reload();

        return BannedWordCreateRes.from(savedBannedWord);
    }

    public Page<BannedWordReadRes> readBannedWords(BannedWordReadPageReq bannedWordReadPageReq) {

        // before
        // Pageable pageable = bannedWordReadPageReq.toPageable();
        // return bannedWordManager.readBannedWordsAndPage(pageable);

        Pageable pageable = bannedWordReadPageReq.toPageable();

        return bannedWordRepository.findAll(pageable).map(BannedWordReadRes::from);
    }

    @Transactional
    public BannedWordDeleteRes deleteBannedWord(Long bannedWordId) {

        // before
        // BannedWord bannedWord = bannedWordManager.getBannedWord(bannedWordId);
        // bannedWordManager.deleteBannedWord(bannedWord);
        // return bannedWordMapper.toDeleteRes(bannedWord);

        BannedWord bannedWord = bannedWordRepository.findById(bannedWordId)
                .orElseThrow(() -> new GlobalException(BannedWordErrorCode.BANNED_WORD_NOT_FOUND));

        bannedWordRepository.delete(bannedWord);

        bannedWordFilter.reload();

        return BannedWordDeleteRes.from(bannedWord);
    }

    @Transactional
    public BannedWordBulkDeleteRes deleteBanWordsByIds(BannedWordDeleteBulkReq request) {

        // before
        // List<BannedWord> banWords = bannedWordManager.findBannedWordsById(ids);
        // bannedWordManager.deleteAllBannedWord(banWords);
        // return bannedWordMapper.toBulkDelete(ids);

        List<Long> ids = request.getIds();
        List<BannedWord> bannedWords = bannedWordRepository.findAllById(ids);

        validateAllIdsExist(ids, bannedWords);
        bannedWordRepository.deleteAllInBatch(bannedWords);

        bannedWordFilter.reload();

        return BannedWordBulkDeleteRes.from(ids);
    }

    // TODO : 추후 별도의 검증 컴포넌트 만들기
    private void validateWordExists(BannedWordCreateReq request) {
        if (!bannedWordRepository.existsByWord(request.getBanWord())) {
            throw new GlobalException(BannedWordErrorCode.DUPLICATED_BANNED_WORD);
        }
    }

    private void validateAllIdsExist(List<Long> ids, List<BannedWord> bannedWords) {

        // before
        // Set<Long> foundIds = bannedWords.stream().map(BannedWord::getId).collect(Collectors.toSet());
        //
        // if (!foundIds.containsAll(ids)) {
        //    throw new GlobalException(BannedWordErrorCode.BANNED_WORD_NOT_FOUND);
        //}

        long distinctIdCount = ids.stream().distinct().count();
        if (bannedWords.size() != distinctIdCount) {
            throw new GlobalException(BannedWordErrorCode.BANNED_WORD_NOT_FOUND);
        }
    }
}
