package com.example.ufo_fi.v2.order.application.bulk;

import com.example.ufo_fi.v2.order.presentation.dto.response.BulkPurchaseFailureRes;
import com.example.ufo_fi.v2.order.presentation.dto.response.BulkPurchaseSuccessRes;
import com.example.ufo_fi.v2.tradepost.domain.TradePost;
import com.example.ufo_fi.v2.user.domain.User;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class PurchaseResult {
    private final List<BulkPurchaseSuccessRes> bulkPurchaseSuccessesRes = new ArrayList<>();
    private final List<BulkPurchaseFailureRes> bulkPurchaseFailureRes = new ArrayList<>();
    private int totalGb;
    private int totalZet;

    public int increaseTotalGB(int tempGb){
        this.totalGb += tempGb;
        return totalGb;
    }

    public int increaseTotalZet(int tempZet){
        this.totalZet += tempZet;
        return totalZet;
    }

    public void addPurchaseSuccess(TradePost tradePost, User seller){
        this.bulkPurchaseSuccessesRes.add(BulkPurchaseSuccessRes.of(tradePost, seller));
    }

    public void addPurchaseFailure(TradePost tradePost, User seller, String failReason){
        this.bulkPurchaseFailureRes.add(BulkPurchaseFailureRes.of(tradePost, seller, failReason));
    }
}
