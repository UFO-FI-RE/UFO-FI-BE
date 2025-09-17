package com.example.ufo_fi.v2.bannedword.application;


import com.example.ufo_fi.v2.bannedword.domain.BannedWord;
import com.example.ufo_fi.v2.bannedword.domain.BannedWordManager;
import com.example.ufo_fi.v2.bannedword.domain.filter.BannedWordFilter;
import com.example.ufo_fi.v2.bannedword.exception.BannedWordErrorCode;
import com.example.ufo_fi.v2.bannedword.presentation.dto.request.BannedWordCreateReq;
import com.example.ufo_fi.v2.bannedword.presentation.dto.request.BannedWordDeleteBulkReq;
import com.example.ufo_fi.v2.bannedword.presentation.dto.request.BannedWordReadPageReq;
import com.example.ufo_fi.v2.bannedword.presentation.dto.response.BannedWordBulkDeleteRes;
import com.example.ufo_fi.v2.bannedword.presentation.dto.response.BannedWordCreateRes;
import com.example.ufo_fi.v2.bannedword.presentation.dto.response.BannedWordDeleteRes;
import com.example.ufo_fi.v2.bannedword.presentation.dto.response.BannedWordReadRes;
import java.util.List;
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

    @Transactional
    public BannedWordCreateRes createBannedWord(BannedWordCreateReq request) {

        BannedWord bannedWord = bannedWordMapper.toBannedWord(request);

        bannedWordManager.validateWordNotExists(bannedWord.getWord());

        BannedWord savedBannedWord = bannedWordManager.saveBannedWord(bannedWord);

        bannedWordFilter.reload();

        return bannedWordMapper.toCreateRes(savedBannedWord);
    }

    public Page<BannedWordReadRes> readBannedWords(BannedWordReadPageReq bannedWordReadPageReq) {

        Pageable pageable = bannedWordReadPageReq.toPageable();

        return bannedWordManager.readBannedWordsAndPage(pageable);
    }

    @Transactional
    public BannedWordDeleteRes deleteBannedWord(Long bannedWordId) {

        BannedWord bannedWord = bannedWordManager.getBannedWord(bannedWordId);

        bannedWordManager.deleteBannedWord(bannedWord);

        bannedWordFilter.reload();

        return bannedWordMapper.toDeleteRes(bannedWord);
    }

    @Transactional
    public BannedWordBulkDeleteRes deleteBanWordsByIds(BannedWordDeleteBulkReq request) {

        List<Long> ids = request.getIds();

        List<BannedWord> banWords = bannedWordManager.findBannedWordsById(ids);

        bannedWordManager.validateAllIdsExist(ids, banWords,
            BannedWordErrorCode.BANNED_WORD_NOT_FOUND);

        bannedWordManager.deleteAllBannedWord(banWords);
        bannedWordFilter.reload();

        return bannedWordMapper.toBulkDelete(ids);
    }

}
