package com.wldrmnd.superstock.request.stock.statistic;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FindStockStatisticRequest {

    private Long id;
    private Long stockId;
}
