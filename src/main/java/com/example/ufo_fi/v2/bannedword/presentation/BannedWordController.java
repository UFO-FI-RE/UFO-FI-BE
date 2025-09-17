package com.example.ufo_fi.v2.bannedword.presentation;


import com.example.ufo_fi.global.response.ResponseBody;
import com.example.ufo_fi.v2.bannedword.application.BannedWordService;
import com.example.ufo_fi.v2.bannedword.presentation.api.BannedWordApiSpec;
import com.example.ufo_fi.v2.bannedword.presentation.dto.request.BannedWordCreateReq;
import com.example.ufo_fi.v2.bannedword.presentation.dto.request.BannedWordDeleteBulkReq;
import com.example.ufo_fi.v2.bannedword.presentation.dto.request.BannedWordReadPageReq;
import com.example.ufo_fi.v2.bannedword.presentation.dto.response.BannedWordBulkDeleteRes;
import com.example.ufo_fi.v2.bannedword.presentation.dto.response.BannedWordCreateRes;
import com.example.ufo_fi.v2.bannedword.presentation.dto.response.BannedWordDeleteRes;
import com.example.ufo_fi.v2.bannedword.presentation.dto.response.BannedWordReadRes;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BannedWordController implements BannedWordApiSpec {

    private final BannedWordService bannedWordService;

    @Override
    public ResponseEntity<ResponseBody<BannedWordCreateRes>> createBannedWord(
        BannedWordCreateReq request) {

        return ResponseEntity.ok(
            ResponseBody.success(
                bannedWordService.createBannedWord(request)
            )
        );
    }

    @Override
    public ResponseEntity<ResponseBody<Page<BannedWordReadRes>>> readBannedWords(
        @Valid BannedWordReadPageReq bannedWordReadPageReq) {

        return ResponseEntity.ok(
            ResponseBody.success(
                bannedWordService.readBannedWords(bannedWordReadPageReq)
            )
        );
    }

    @Override
    public ResponseEntity<ResponseBody<BannedWordDeleteRes>> deleteBannedWord(Long id) {

        return ResponseEntity.ok(
            ResponseBody.success(
                bannedWordService.deleteBannedWord(id)
            )
        );
    }

    @Override
    public ResponseEntity<ResponseBody<BannedWordBulkDeleteRes>> deleteBannedWords(
        BannedWordDeleteBulkReq request) {

        return ResponseEntity.ok(
            ResponseBody.success(
                bannedWordService.deleteBanWordsByIds(request)
            )
        );
    }
}