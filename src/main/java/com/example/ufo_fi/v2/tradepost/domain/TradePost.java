package com.example.ufo_fi.v2.tradepost.domain;

import com.example.ufo_fi.v2.plan.domain.Carrier;
import com.example.ufo_fi.v2.plan.domain.MobileDataType;
import com.example.ufo_fi.v2.report.domain.Report;
import com.example.ufo_fi.v2.tradepost.exception.TradePostErrorCode;
import com.example.ufo_fi.v2.tradepost.presentation.dto.request.TradePostUpdateReq;
import com.example.ufo_fi.v2.user.domain.User;
import com.example.ufo_fi.global.exception.GlobalException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "trade_posts")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class TradePost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "mobile_data_type")
    private MobileDataType mobileDataType;

    @Enumerated(EnumType.STRING)
    @Column(name = "carrier")
    private Carrier carrier;

    @Column(name = "sell_mobile_data_capacity_gb")
    private Integer sellMobileDataCapacityGb;

    @Column(name = "title")
    private String title;

    @Column(name = "zet_per_unit")
    private Integer zetPerUnit;

    @Column(name = "total_zet")
    private Integer totalZet;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TradePostStatus tradePostStatus;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "tradePost")
    @Builder.Default
    private List<Report> reports = new ArrayList<>();


    public void saveTotalPrice() {

        if (this.zetPerUnit != null && this.sellMobileDataCapacityGb > 0) {
            this.totalZet = this.zetPerUnit * this.sellMobileDataCapacityGb;
        } else {
            this.totalZet = 0;
        }
    }

    public int delete() {

        if (!this.tradePostStatus.equals(TradePostStatus.SELLING)) {

            throw new GlobalException(TradePostErrorCode.CANNOT_DELETE_NOT_SELLING_POST);
        }

        this.softDeleteAndStatusDelete();

        return this.sellMobileDataCapacityGb;
    }

    public void softDeleteAndStatusDelete() {
        this.tradePostStatus = TradePostStatus.DELETED;
    }

    public void update(TradePostUpdateReq request) {

        if (request.getTitle() != null) {
            this.title = request.getTitle();
        }

        if (request.getZetPerUnit() != null) {
            this.zetPerUnit = request.getZetPerUnit();
        }

        if (request.getSellMobileDataCapacityGb() != null) {
            this.sellMobileDataCapacityGb = request.getSellMobileDataCapacityGb();
        }

        saveTotalPrice();
    }

    public void verifyOwner(User anotehrUser) {

        if (!this.user.getId().equals(anotehrUser.getId())) {

            throw new GlobalException(TradePostErrorCode.NO_AUTHORITY);
        }
    }

    public void validatePurchaseStatus(User buyer) {
        if (this.tradePostStatus.equals(TradePostStatus.SOLD_OUT)) {
            throw new GlobalException(TradePostErrorCode.ALREADY_SOLDOUT);
        }

        if (this.tradePostStatus.equals(TradePostStatus.DELETED)) {
            throw new GlobalException(TradePostErrorCode.ALREADY_DELETE);
        }

        if (this.tradePostStatus.equals(TradePostStatus.REPORTED)) {
            throw new GlobalException(TradePostErrorCode.ALREADY_REPORTED);
        }

        if (this.user.getId().equals(buyer.getId())) {
            throw new GlobalException(TradePostErrorCode.CANT_PURCHASE_MYSELF);
        }
    }

    public void updateStatusSoldOut() {
        this.tradePostStatus = TradePostStatus.SOLD_OUT;
    }

    public void updateStatusReported() {
        this.tradePostStatus = TradePostStatus.REPORTED;
    }

    public void updateStatusSelling() {
        this.tradePostStatus = TradePostStatus.SELLING;
    }

    public void updateStatusExpired() {
        this.tradePostStatus = TradePostStatus.EXPIRED;
    }

    public void updateStatus(TradePostStatus tradePostStatus) {
        this.tradePostStatus = tradePostStatus;
    }

    public boolean canSellingNow() {
        YearMonth created = YearMonth.from(this.createdAt);
        YearMonth current = YearMonth.from(LocalDateTime.now());
        return !current.isAfter(created.plusMonths(1));
    }
}