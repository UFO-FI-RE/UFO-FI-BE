package com.example.ufo_fi.v2.interestedpost.presentation;

import com.example.ufo_fi.global.response.ResponseBody;
import com.example.ufo_fi.v2.auth.presentation.AuthContextHolder;
import com.example.ufo_fi.v2.interestedpost.application.InterestedPostService;
import com.example.ufo_fi.v2.interestedpost.presentation.api.InterestedPostApiSpec;
import com.example.ufo_fi.v2.interestedpost.presentation.dto.request.InterestedPostUpdateReq;
import com.example.ufo_fi.v2.interestedpost.presentation.dto.response.InterestedPostRes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class InterestedPostController implements InterestedPostApiSpec {

    private final InterestedPostService interestedPostService;

    @Override
    public ResponseEntity<ResponseBody<Void>> updateInterestedPost(
            InterestedPostUpdateReq request
    ) {

        interestedPostService.updateInterestedPost(AuthContextHolder.getUserId(), request);
        return ResponseEntity.ok(ResponseBody.noContent());
    }

    @Override
    public ResponseEntity<ResponseBody<InterestedPostRes>> readInterestedPost(
    ) {
        return ResponseEntity.ok(ResponseBody.success(interestedPostService.readInterestedPost(AuthContextHolder.getUserId())));
    }
}
