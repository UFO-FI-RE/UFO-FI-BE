package com.example.ufo_fi.v2.report.domain;

import com.example.ufo_fi.global.exception.GlobalException;
import com.example.ufo_fi.v2.report.exception.ReportErrorCode;
import com.example.ufo_fi.v2.report.presentation.dto.request.ReportCreateReq;
import com.example.ufo_fi.v2.tradepost.domain.TradePost;
import com.example.ufo_fi.v2.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDateTime;
import java.util.Objects;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "reports")
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "content", nullable = false)
    private String content;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reported_user_id", nullable = false)
    private User reportedUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporting_user_id", nullable = false)
    private User reportingUser;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trade_post_id", nullable = false)
    private TradePost tradePost;

    @Builder(access = AccessLevel.PRIVATE)
    public Report(String content, User reportedUser, User reportingUser, TradePost tradePost) {
        this.content = requireContent(content);
        this.reportedUser = requireReportedUser(reportedUser);
        this.reportingUser = requireReportingUser(reportingUser);
        this.tradePost = requireTradePost(tradePost);
        validateReportUser(reportedUser, reportingUser);
    }

    public static Report of(User reportingUser, User reportedUser, TradePost tradePost, String content) {
        return Report.builder()
            .content(content)
            .reportedUser(reportedUser)
            .reportingUser(reportingUser)
            .tradePost(tradePost)
            .build();
    }

    //불변식 모음
    private String requireContent(String content) {
        if(content == null || content.isEmpty()) throw new GlobalException(ReportErrorCode.CONTENT_NOT_NULL);
        if(content.length() > 100) throw new GlobalException(ReportErrorCode.CONTENT_TOO_LONG);
        return content;
    }

    private User requireReportedUser(User user) {
        if(user == null) throw new GlobalException(ReportErrorCode.REPORTED_USER_NOT_NULL);
        return user;
    }

    private User requireReportingUser(User user) {
        if(user == null) throw new GlobalException(ReportErrorCode.REPORTING_USER_NOT_NULL);
        return user;
    }

    private TradePost requireTradePost(TradePost tradePost) {
        if(tradePost == null) throw new GlobalException(ReportErrorCode.TRADE_POST_NOT_NULL);
        return tradePost;
    }

    private void validateReportUser(User reportedUser, User reportingUser) {
        if(Objects.equals(reportedUser.getId(), reportingUser.getId()))
            throw new GlobalException(ReportErrorCode.REPORTED_REPORTING_USER_NOT_EQUAL);
    }
}
