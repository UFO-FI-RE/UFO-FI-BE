package com.example.ufo_fi.v2.order.presentation.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleHistoriesRes {

    private List<? extends SaleHistoryRes> saleHistoriesRes;


}