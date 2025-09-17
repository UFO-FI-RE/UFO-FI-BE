package com.example.ufo_fi.v2.report.domain;

import com.example.ufo_fi.v2.report.persistence.ReportRepository;
import com.example.ufo_fi.v2.tradepost.domain.TradePost;
import com.example.ufo_fi.v2.user.domain.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReportManager {

    private final ReportRepository reportRepository;

    public void save(Report report) {
        reportRepository.save(report);
    }

    public int countReportByTradePost(TradePost tradePost) {
        return reportRepository.countByTradePost(tradePost);
    }

    public int countByUser(User user) {
        return reportRepository.countByReportedUser(user);
    }

    public List<Report> findByTradePost(TradePost tradePost) {
        return reportRepository.findByTradePost(tradePost);
    }

    public void deleteAll(List<Report> reports) {

    }

    public boolean canSellingNow(TradePost tradePost) {
        return tradePost.canSellingNow();
    }
}
