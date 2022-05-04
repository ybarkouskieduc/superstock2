package com.wldrmnd.superstock.request.stock;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Builder
@Getter
public class CalculateStockProfitRequest {

    private Date dateFrom;
    private Date dateTo;
    private Long stockId;
    private Long userId;
}
