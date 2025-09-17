package com.example.ufo_fi.v2.bannedword.presentation.dto.response;


import com.example.ufo_fi.v2.bannedword.domain.BannedWord;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "금칙어 조회 응답 DTO")
public class BannedWordReadRes {

    @Schema(description = "금칙어 ID", example = "1")
    private Long id;

    @Schema(description = "금칙어 문자열", example = "시발")
    private String word;

    public static BannedWordReadRes from(final BannedWord bannedWord) {
        return BannedWordReadRes.builder()
            .id(bannedWord.getId())
            .word(bannedWord.getWord())
            .build();
    }
}