package com.example.ufo_fi.v2.bannedword.presentation.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BannedWordDeleteRes {

    @Schema(description = "금칙어 ID", example = "1")
    private Long id;
}
