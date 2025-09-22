package com.example.ufo_fi.v2.report.presentation;

import com.example.ufo_fi.v2.auth.presentation.AuthContextHolder;
import com.example.ufo_fi.v2.report.application.ReportService;
import com.example.ufo_fi.v2.report.presentation.dto.request.ReportCreateReq;
import com.example.ufo_fi.v2.report.presentation.dto.request.ReportRollBackReq;
import com.example.ufo_fi.v2.report.presentation.dto.response.RollBackReportsReadRes;
import com.example.ufo_fi.global.response.ResponseBody;
import com.example.ufo_fi.v2.report.presentation.api.ReportApiSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReportController implements ReportApiSpec {

    private final ReportService reportService;

    @Override
    public ResponseEntity<ResponseBody<Void>> reportTradePost(
        Long tradePostId,
        ReportCreateReq reportCreateReq
    ) {
        reportService.reportTradePost(AuthContextHolder.getUserId(), tradePostId, reportCreateReq);
        return ResponseEntity.ok(ResponseBody.noContent());
    }

    @Override
    public ResponseEntity<ResponseBody<RollBackReportsReadRes>> readRollBackRegistration() {
        return ResponseEntity.ok(
            ResponseBody.success(
                reportService.readRollBackRegistration()));
    }

    @Override
    public ResponseEntity<ResponseBody<Void>> approveRollBackRegistration(
        ReportRollBackReq reportRollBackReq
    ) {
        reportService.approveRollBackRegistration(reportRollBackReq);
        return ResponseEntity.ok(ResponseBody.noContent());
    }
}
