package com.example.ufo_fi.v2.report.application;

import com.example.ufo_fi.v2.report.domain.Report;
import com.example.ufo_fi.v2.report.presentation.dto.request.ReportCreateReq;
import com.example.ufo_fi.v2.report.presentation.dto.response.RollBackReportsReadRes;
import com.example.ufo_fi.v2.tradepost.domain.TradePost;
import com.example.ufo_fi.v2.user.domain.User;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ReportMapper {

    public Report toReport(
        User user, User reportedUser, TradePost tradePost, ReportCreateReq reportCreateReq
    ) {

        return Report.of(user, reportedUser, tradePost, reportCreateReq);
    }

    public RollBackReportsReadRes toRollBackReportsReadRes(List<TradePost> reportedPosts) {

        return RollBackReportsReadRes.from(reportedPosts);
    }
}
