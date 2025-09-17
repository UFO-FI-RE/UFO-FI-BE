package com.example.ufo_fi.v2.auth.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshReissueRes {
    private boolean isReissueSuccess;

    public static RefreshReissueRes from(boolean isReissueSuccess){
        return RefreshReissueRes.builder()
                .isReissueSuccess(true)
                .build();
    }
}
