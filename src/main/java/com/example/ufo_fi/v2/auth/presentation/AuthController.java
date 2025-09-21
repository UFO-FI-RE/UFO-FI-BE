package com.example.ufo_fi.v2.auth.presentation;

import com.example.ufo_fi.global.response.ResponseBody;
import com.example.ufo_fi.v2.auth.application.AuthFacadeService;
import com.example.ufo_fi.v2.auth.presentation.dto.KakaoCallBackParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthFacadeService authFacadeService;

    @GetMapping("/kakao")
    public ResponseEntity<ResponseBody<Void>> kakaoLogin(
            @ModelAttribute KakaoCallBackParam kakaoCallBackParam
    ) {
        String jwt = authFacadeService.kakaoLogin(kakaoCallBackParam);

        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, jwt)
                .body(ResponseBody.noContent());
    }
}
