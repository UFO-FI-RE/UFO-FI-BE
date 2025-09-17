package com.example.ufo_fi.v2.auth.presentation.api;

import com.example.ufo_fi.global.response.ResponseBody;
import com.example.ufo_fi.v2.auth.application.principal.DefaultUserPrincipal;
import com.example.ufo_fi.v2.auth.presentation.dto.response.RefreshReissueRes;
import com.example.ufo_fi.v2.user.presentation.dto.response.AnotherUserInfoReadRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Tag(name = "Auth API", description = "인증관련 API")
public interface AuthApiSpec {

    @Operation(summary = "로그아웃 API", description = "JWT와 Refresh 토큰을 삭제한다.")
    @ApiResponse(useReturnTypeSchema = true)
    @PostMapping("/logout")
    ResponseEntity<ResponseBody<Void>> logout(
        @AuthenticationPrincipal DefaultUserPrincipal defaultUserPrincipal,
        HttpServletRequest httpServletRequest,
        HttpServletResponse httpServletResponse
    );


    @Operation(summary = "jwt 토큰 갱신 API", description = "jwt 토큰을 갱신한다.")
    @ApiResponse(useReturnTypeSchema = true)
    @GetMapping("/refresh")
    ResponseEntity<ResponseBody<RefreshReissueRes>> reissueRefresh(
        @AuthenticationPrincipal DefaultUserPrincipal defaultUserPrincipal,
        HttpServletRequest request,
        HttpServletResponse response
    );
}
