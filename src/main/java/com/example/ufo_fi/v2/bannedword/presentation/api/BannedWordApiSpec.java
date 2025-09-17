package com.example.ufo_fi.v2.bannedword.presentation.api;

import com.example.ufo_fi.global.response.ResponseBody;
import com.example.ufo_fi.v2.bannedword.presentation.dto.request.BannedWordCreateReq;
import com.example.ufo_fi.v2.bannedword.presentation.dto.request.BannedWordDeleteBulkReq;
import com.example.ufo_fi.v2.bannedword.presentation.dto.request.BannedWordReadPageReq;
import com.example.ufo_fi.v2.bannedword.presentation.dto.response.BannedWordBulkDeleteRes;
import com.example.ufo_fi.v2.bannedword.presentation.dto.response.BannedWordCreateRes;
import com.example.ufo_fi.v2.bannedword.presentation.dto.response.BannedWordDeleteRes;
import com.example.ufo_fi.v2.bannedword.presentation.dto.response.BannedWordReadRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface BannedWordApiSpec {

    @Operation(summary = "금칙어 등록", description = "금칙어 등록 : 관리자 로그인 필요")
    @ApiResponse(useReturnTypeSchema = true)
    @PostMapping("/admin/banned-words")
    ResponseEntity<ResponseBody<BannedWordCreateRes>> createBannedWord(
        @Valid @RequestBody BannedWordCreateReq request
    );

    @Operation(summary = "금칙어 전체 조회", description = "금칙어 전체 조회 : 관리자 로그인 필요")
    @ApiResponse(useReturnTypeSchema = true)
    @GetMapping("/admin/banned-words")
    ResponseEntity<ResponseBody<Page<BannedWordReadRes>>> readBannedWords(
        @Valid @ParameterObject BannedWordReadPageReq request
    );

    @Operation(summary = "금칙어 단일 삭제", description = "금칙어 단일 삭제 : 관리자 로그인 필요")
    @ApiResponse(useReturnTypeSchema = true)
    @DeleteMapping("/admin/banned-words/{bannedWordId}")
    ResponseEntity<ResponseBody<BannedWordDeleteRes>> deleteBannedWord(
        @Parameter(description = "삭제할 금칙어 ID")
        @PathVariable("bannedWordId") Long id
    );

    @Operation(summary = "금칙어 일괄 삭제", description = "금칙어 일괄 삭제 : 관리자 로그인 필요")
    @ApiResponse(useReturnTypeSchema = true)
    @DeleteMapping("/admin/banned-words")
    ResponseEntity<ResponseBody<BannedWordBulkDeleteRes>> deleteBannedWords(
        @Parameter(description = "삭제할 금칙어 ID 목록")
        @Valid @RequestBody BannedWordDeleteBulkReq request
    );

}
