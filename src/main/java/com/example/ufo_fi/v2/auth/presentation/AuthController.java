package com.example.ufo_fi.v2.auth.presentation;

import com.example.ufo_fi.global.response.ResponseBody;
import com.example.ufo_fi.v2.auth.application.LogoutService;
import com.example.ufo_fi.v2.auth.application.principal.DefaultUserPrincipal;
import com.example.ufo_fi.v2.auth.application.refresh.RefreshService;
import com.example.ufo_fi.v2.auth.presentation.api.AuthApiSpec;
import com.example.ufo_fi.v2.auth.presentation.dto.response.RefreshReissueRes;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApiSpec {
    private final LogoutService logoutService;
    private final RefreshService refreshService;


    @Override
    public ResponseEntity<ResponseBody<Void>> logout(
        DefaultUserPrincipal defaultUserPrincipal,
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        logoutService.logout(request, response, defaultUserPrincipal.getId());
        return ResponseEntity.ok(ResponseBody.noContent());
    }

    /**
     * 만약, JWT 토큰이 만료되어, API 호출에서 예외를 반환하였다면, 이 API를 호출해, Jwt토큰을 갱신해줘야 한다.
     * response에 새롭게 갱신된 jwt 토큰을 호출해주어야 한다.
     */
    @Override
    public ResponseEntity<ResponseBody<RefreshReissueRes>> reissueRefresh(
        DefaultUserPrincipal defaultUserPrincipal,
        HttpServletRequest request,
        HttpServletResponse response) {
        return ResponseEntity.ok(
            ResponseBody.success(
                refreshService.reissueJwt(defaultUserPrincipal, request, response)));
    }

}
