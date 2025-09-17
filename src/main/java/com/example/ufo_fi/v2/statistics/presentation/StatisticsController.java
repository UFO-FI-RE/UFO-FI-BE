package com.example.ufo_fi.v2.statistics.presentation;

import com.example.ufo_fi.v2.statistics.application.StatisticsService;
import com.example.ufo_fi.v2.statistics.presentation.api.StatisticsApiSpec;
import com.example.ufo_fi.v2.statistics.presentation.dto.response.StatisticsReportsRes;
import com.example.ufo_fi.v2.statistics.presentation.dto.response.StatisticsRes;
import com.example.ufo_fi.global.response.ResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StatisticsController implements StatisticsApiSpec {
    private final StatisticsService statisticsService;

    @Override
    public ResponseEntity<ResponseBody<StatisticsRes>> readStatistics() {
        return ResponseEntity.ok(
            ResponseBody.success(
                statisticsService.readStatistics()));
    }

    @Override
    public ResponseEntity<ResponseBody<StatisticsReportsRes>> readStatisticsReports() {
        return ResponseEntity.ok(
            ResponseBody.success(
                statisticsService.readReportsStatistics()));
    }
}
