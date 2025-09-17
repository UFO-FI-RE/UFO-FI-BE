package com.example.ufo_fi.v2.bannedword.application;

import com.example.ufo_fi.v2.bannedword.domain.BannedWord;
import com.example.ufo_fi.v2.bannedword.presentation.dto.request.BannedWordCreateReq;
import com.example.ufo_fi.v2.bannedword.presentation.dto.response.BannedWordBulkDeleteRes;
import com.example.ufo_fi.v2.bannedword.presentation.dto.response.BannedWordCreateRes;
import com.example.ufo_fi.v2.bannedword.presentation.dto.response.BannedWordDeleteRes;
import java.util.List;
import lombok.Builder;
import org.springframework.stereotype.Component;

@Builder
@Component
public class BannedWordMapper {

    public BannedWord toBannedWord(final BannedWordCreateReq request) {
        return BannedWord.builder()
            .word(request.getBanWord())
            .build();
    }

    public BannedWordCreateRes toCreateRes(final BannedWord bannedWord) {
        return BannedWordCreateRes.builder()
            .id(bannedWord.getId())
            .word(bannedWord.getWord())
            .build();
    }

    public BannedWordDeleteRes toDeleteRes(final BannedWord bannedWord) {

        return BannedWordDeleteRes.builder()
            .id(bannedWord.getId())
            .build();
    }

    public BannedWordBulkDeleteRes toBulkDelete(List<Long> ids) {
        return BannedWordBulkDeleteRes.builder()
            .deletedIds(ids)
            .deletedCount(ids.size())
            .build();
    }
}
