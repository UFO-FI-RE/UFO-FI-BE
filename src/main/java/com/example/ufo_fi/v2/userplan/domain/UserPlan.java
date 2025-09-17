package com.example.ufo_fi.v2.userplan.domain;

import com.example.ufo_fi.global.exception.GlobalException;
import com.example.ufo_fi.v2.plan.domain.Plan;
import com.example.ufo_fi.v2.tradepost.exception.TradePostErrorCode;
import com.example.ufo_fi.v2.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_plans")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "sellable_data_amount")
    private Integer sellableDataAmount;

    @Column(name = "purchase_data_amount")
    private Integer purchaseDataAmount;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    private Plan plan;

    public static UserPlan from(final Plan plan) {
        return UserPlan.builder()
            .sellableDataAmount(plan.getSellMobileDataCapacityGb())
            .purchaseDataAmount(0)
            .plan(plan)
            .build();
    }

    public static UserPlan of(Plan plan, User user) {
        return UserPlan.builder()
            .sellableDataAmount(plan.getSellMobileDataCapacityGb())
            .purchaseDataAmount(0)
            .user(user)
            .plan(plan)
            .build();
    }

    public void updateSellableDataAmount(int originData, int newData) {

        this.increaseSellableDataAmount(originData);
        this.subtractSellableDataAmount(newData);
    }

    private void subtractSellableDataAmount(int requestSellData) {

        if (requestSellData < 0 || requestSellData > this.sellableDataAmount) {

            throw new GlobalException(TradePostErrorCode.EXCEED_SELL_CAPACITY);
        }

        this.sellableDataAmount -= requestSellData;
    }

    public void increaseSellableDataAmount(int restore) {

        if (restore < 0 || restore + this.sellableDataAmount > plan.getSellMobileDataCapacityGb()) {

            throw new GlobalException(TradePostErrorCode.EXCEED_RESTORE_CAPACITY);
        }

        this.sellableDataAmount += restore;
    }

    public void increasePurchaseAmount(int purchaseDataAmount) {
        this.purchaseDataAmount += purchaseDataAmount;
    }

    public void update(Plan plan) {
        this.plan = plan;
        this.sellableDataAmount = plan.getSellMobileDataCapacityGb();
    }

    public Plan getValidatedPlan() {

        if (this.plan == null) {
            throw new GlobalException(TradePostErrorCode.PLAN_NOT_FOUND);
        }

        return this.plan;
    }

    public void validateAndSubtractForSale(int dataAmountToSell) {

        if (dataAmountToSell > this.sellableDataAmount) {
            throw new GlobalException(TradePostErrorCode.EXCEED_SELL_CAPACITY);
        }

        this.sellableDataAmount -= dataAmountToSell;
    }
}
